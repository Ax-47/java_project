package com.example.restservice.Auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(@NotBlank String refresh_token) {}
