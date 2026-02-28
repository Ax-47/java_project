package com.example.restservice.Auth.dto;

import java.util.Set;

public record RegisterClientRequestDTO(
    String clientId,
    String clientSecret,
    Set<String> scopes) {
}
