package com.example.restservice.Auth.dto;

public record SignInResponseDTO(
        String access_token,
        String refresh_token) {
}
