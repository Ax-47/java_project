package com.example.restservice.Auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequestDTO(
    @NotBlank String name,
    @Size(min = 8, message = "Password must be at least 8 characters") String password) {}
