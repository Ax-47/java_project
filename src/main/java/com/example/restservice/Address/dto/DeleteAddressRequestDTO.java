package com.example.restservice.Address.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record DeleteAddressRequestDTO(
    @NotNull(message = "Address ID is required") UUID addressId,
    @NotNull(message = "User ID is required") UUID userId
) {}
