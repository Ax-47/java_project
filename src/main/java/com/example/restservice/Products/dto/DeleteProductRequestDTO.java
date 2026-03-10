package com.example.restservice.Products.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record DeleteProductRequestDTO(
        @NotNull(message = "Product ID is required") UUID productId,

        @NotNull(message = "User ID is required") UUID userId) {
}