package com.example.restservice.Auth.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Auth.dto.SignInRequestDTO;
import com.example.restservice.Auth.dto.SignInResponseDTO;
import com.example.restservice.Auth.usecases.SignInUsecase;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  private final SignInUsecase signInUsecase;

  public AuthenticationController(SignInUsecase signInUsecase) {
    this.signInUsecase = signInUsecase;
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
  public ResponseEntity<SignInResponseDTO> signin(@RequestBody @Validated SignInRequestDTO request) {
    return ResponseEntity.ok(signInUsecase.execute(request));
  }

}
