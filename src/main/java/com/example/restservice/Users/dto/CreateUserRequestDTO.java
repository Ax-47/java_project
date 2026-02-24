package com.example.restservice.Users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDTO(
    @NotBlank String name,
    @Size(min = 8, message = "Password must be at least 8 characters") String password) {
}
