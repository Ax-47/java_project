package com.example.restservice.Auth.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Auth.dto.LoginRequestDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @GetMapping("/me")
  public Object me(Authentication authentication) {
    return authentication.getPrincipal();
  }

}
