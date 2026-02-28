package com.example.restservice.Address.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record DeleteAddressRequestDTO(
                @NotNull(message = "Address ID is required") UUID addressId,
                // TODO: delete ts use userId from auth intead of ts.
                @NotNull(message = "User ID is required") UUID userId) {
}
