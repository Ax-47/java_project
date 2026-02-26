
package com.example.restservice.Auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank String username,
        @Size(min = 8, message = "Password must be at least 8 characters") String password) {
}
