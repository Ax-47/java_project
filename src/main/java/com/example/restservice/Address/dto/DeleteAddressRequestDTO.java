package com.example.restservice.Address.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record DeleteAddressRequestDTO(
    @NotNull(message = "Address ID is required") UUID addressId,
    @NotNull(message = "User ID is required") UUID userId) {}
