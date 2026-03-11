package com.example.restservice.Auth.dto;

public record TokenResponseDTO(
                                String accessToken,
                                String refreshToken) {
}
