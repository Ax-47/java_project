package com.example.restservice.Auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Auth.dto.AuthenticatedUser;
import com.example.restservice.Auth.dto.SignInRequestDTO;
import com.example.restservice.Auth.dto.SignInResponseDTO;
import com.example.restservice.Auth.usecases.SignInUsecase;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  private final SignInUsecase signInUsecase;

  public AuthenticationController(SignInUsecase signInUsecase) {
    this.signInUsecase = signInUsecase;
  }

  @GetMapping("/me")
  public AuthenticatedUser me(@AuthenticationPrincipal AuthenticatedUser user) {
    return user;
  }

  @PostMapping("/signin")
  public ResponseEntity<SignInResponseDTO> signin(@RequestBody @Validated SignInRequestDTO request) {
    return ResponseEntity.ok(signInUsecase.execute(request));
  }

}
