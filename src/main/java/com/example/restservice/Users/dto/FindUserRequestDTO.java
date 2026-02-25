package com.example.restservice.Users.dto;

import jakarta.validation.constraints.NotBlank;

public record FindUserRequestDTO(
    @NotBlank String name) {
}
