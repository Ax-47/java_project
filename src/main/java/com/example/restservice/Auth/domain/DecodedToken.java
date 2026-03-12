package com.example.restservice.Auth.domain;

import java.util.UUID;

public record DecodedToken(UUID tokenId, UUID userId, String secret, String type) {}
