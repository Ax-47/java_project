package com.example.restservice.Auth.domain;

import java.util.UUID;

public record UserPrincipal(UUID id, String username) {}
