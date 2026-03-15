package com.example.restservice.Auth.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Auth.dto.SignInRequestDTO;
import com.example.restservice.Auth.dto.TokenResponseDTO;
import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Auth.usecases.RefreshTokenUsecase;
import com.example.restservice.Auth.usecases.SignInUsecase;
import com.example.restservice.Auth.usecases.SignOutUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthenticationController {

  private final SignInUsecase signInUsecase;
  private final RefreshTokenUsecase refreshTokenUsecase;
  private final SignOutUsecase signOutUsecase;

  private long accessTokenExpiredInSeconds;

  private long refreshTokenExpiredInSeconds;

  public AuthenticationController(
      SignInUsecase signInUsecase,
      SignOutUsecase signOutUsecase,
      RefreshTokenUsecase refreshTokenUsecase,
      @Value("${token.access-token-expired-in-seconds}") long accessTokenExpiredInSeconds,
      @Value("${token.refresh-token-expired-in-seconds}") long refreshTokenExpiredInSeconds) {
    this.signInUsecase = signInUsecase;
    this.refreshTokenUsecase = refreshTokenUsecase;
    this.signOutUsecase = signOutUsecase;
    this.accessTokenExpiredInSeconds = accessTokenExpiredInSeconds;
    this.refreshTokenExpiredInSeconds = refreshTokenExpiredInSeconds;
  }

  @Operation(summary = "Get current user")
  @SecurityRequirement(name = "bearerAuth")
  @GetMapping("/me")
  public Map<String, Object> me(@AuthenticationPrincipal UserPrincipalDTO userPrincipal) {
    return Map.of("user", userPrincipal);
  }

  @Operation(summary = "Admin only endpoint")
  @SecurityRequirement(name = "bearerAuth")
  @GetMapping("/admin")
  @RolesAllowed("ADMIN")
  public Map<String, Object> admin(@AuthenticationPrincipal UserPrincipalDTO userPrincipal) {
    return Map.of("user", userPrincipal);
  }

  @PostMapping("/signin")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Login success"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
      })
  public ResponseEntity<TokenResponseDTO> signin(
      @ModelAttribute @Validated SignInRequestDTO request, HttpServletResponse response) {

    TokenResponseDTO tokens = signInUsecase.execute(request);

    Cookie accessCookie = new Cookie("access_token", tokens.access_token());
    accessCookie.setHttpOnly(true);
    accessCookie.setPath("/");
    accessCookie.setMaxAge((int) accessTokenExpiredInSeconds);
    response.addCookie(accessCookie);
    Cookie refreshCookie = new Cookie("refresh_token", tokens.refresh_token());
    refreshCookie.setHttpOnly(true);
    refreshCookie.setPath("/");
    refreshCookie.setMaxAge((int) refreshTokenExpiredInSeconds);
    response.addCookie(refreshCookie);
    return ResponseEntity.ok(tokens);
  }

  @PostMapping("/refresh")
  @Operation(summary = "Refresh access token")
  @SecurityRequirement(name = "bearerAuth")
  public ResponseEntity<TokenResponseDTO> refresh(
      @CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {
    TokenResponseDTO tokens = refreshTokenUsecase.execute(refreshToken);

    Cookie accessCookie = new Cookie("access_token", tokens.access_token());
    accessCookie.setHttpOnly(true);
    accessCookie.setPath("/");
    accessCookie.setMaxAge((int) accessTokenExpiredInSeconds);
    response.addCookie(accessCookie);
    Cookie refreshCookie = new Cookie("refresh_token", tokens.refresh_token());
    refreshCookie.setHttpOnly(true);
    refreshCookie.setPath("/");
    refreshCookie.setMaxAge((int) refreshTokenExpiredInSeconds);
    response.addCookie(refreshCookie);
    return ResponseEntity.ok(refreshTokenUsecase.execute(refreshToken));
  }

  @Operation(summary = "Sign out")
  @SecurityRequirement(name = "bearerAuth")
  @PostMapping("/signout")
  public ResponseEntity<Void> logout(
      @CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {

    Cookie cookie = new Cookie("access_token", null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);

    Cookie rcookie = new Cookie("refresh_token", null);
    rcookie.setMaxAge(0);
    rcookie.setPath("/");
    response.addCookie(rcookie);
    signOutUsecase.execute(refreshToken);
    return ResponseEntity.noContent().build();
  }
}
