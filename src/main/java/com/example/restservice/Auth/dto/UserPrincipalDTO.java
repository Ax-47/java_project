package com.example.restservice.Auth.dto;

import java.util.UUID;

public record UserPrincipalDTO(UUID id, String username, String role) {}
