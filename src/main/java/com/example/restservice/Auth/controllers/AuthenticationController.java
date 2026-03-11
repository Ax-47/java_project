package com.example.restservice.Auth.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Auth.dto.SignInRequestDTO;
import com.example.restservice.Auth.dto.TokenResponseDTO;
import com.example.restservice.Auth.usecases.RefreshTokenUsecase;
import com.example.restservice.Auth.usecases.SignInUsecase;
import com.example.restservice.Auth.usecases.SignOutUsecase;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  private final SignInUsecase signInUsecase;
  private final RefreshTokenUsecase refreshTokenUsecase;
  private final SignOutUsecase signOutUsecase;

  public AuthenticationController(
      SignInUsecase signInUsecase,
      SignOutUsecase signOutUsecase,
      RefreshTokenUsecase refreshTokenUsecase) {
    this.signInUsecase = signInUsecase;
    this.refreshTokenUsecase = refreshTokenUsecase;
    this.signOutUsecase = signOutUsecase;

  }

  @GetMapping("/me")
  public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
    return jwt.getClaims();
  }

  @GetMapping("/admin")
  @RolesAllowed("ADMIN")
  public Map<String, Object> admin(@AuthenticationPrincipal Jwt jwt) {
    return jwt.getClaims();
  }

  @PostMapping("/signin")
  public ResponseEntity<TokenResponseDTO> signin(@RequestBody @Validated SignInRequestDTO request) {
    return ResponseEntity.ok(signInUsecase.execute(request));
  }

  @PostMapping("/refresh")
  public ResponseEntity<TokenResponseDTO> refresh(@RequestHeader("Authorization") String bearer) {
    String refreshToken = bearer.substring(7);
    return ResponseEntity.ok(refreshTokenUsecase.execute(refreshToken));
  }

  @PostMapping("/signout")
  public ResponseEntity<Void> logout(
      @RequestHeader("Authorization") String bearer) {
    String refreshToken = bearer.substring(7);
    signOutUsecase.execute(refreshToken);
    return ResponseEntity.noContent().build();
  }
}
