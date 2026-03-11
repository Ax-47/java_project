package com.example.restservice.Auth.dto;

public record TokenResponseDTO(
                                                                String access_token,
                                                                String refresh_token) {
}
