package com.example.restservice.Auth.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Auth.dto.RegisterClientRequestDTO;
// import com.example.restservice.Auth.usecases.RegisterClientUsecase;

@RestController
@RequestMapping("/auth")
public class AuthController {

  // private final RegisterClientUsecase registerClientUsecase;
  //
  // public AuthController(RegisterClientUsecase registerClientUsecase) {
  // this.registerClientUsecase = registerClientUsecase;
  // }
  //
  @GetMapping("/me")
  public ResponseEntity<?> me(Authentication authentication) {
    return ResponseEntity.ok(authentication.getName());
  }

  // @PostMapping("/clients")
  // public ResponseEntity<?> register(@RequestBody RegisterClientRequestDTO
  // request) {
  //
  // registerClientUsecase.execute(
  // request.clientId(),
  // request.clientSecret(),
  // request.scopes());
  //
  // return ResponseEntity.created(URI.create("/auth/clients/" +
  // request.clientId()))
  // .build();
  // }
  //
  // // FIX: Hide the algorithm
  // @PostMapping("/logout")
  // public ResponseEntity<Void> logout(@RequestParam String token,
  // OAuth2AuthorizationService service) {
  // OAuth2Authorization authorization = service.findByToken(token,
  // OAuth2TokenType.REFRESH_TOKEN);
  // if (authorization != null) {
  // service.remove(authorization);
  // }
  // return ResponseEntity.noContent().build();
  // }
}
