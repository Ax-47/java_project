package com.example.restservice.Address.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteAddressRequestDTO(
        @NotNull(message = "Address ID is required")
        Long addressId,

        @NotNull(message = "User ID is required")
        Long userId
) {
}
