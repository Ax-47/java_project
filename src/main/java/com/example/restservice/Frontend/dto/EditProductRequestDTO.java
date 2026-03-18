package com.example.restservice.Frontend.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.*;

public record EditProductRequestDTO(
    @NotBlank(message = "Product name is required")
        @Size(max = 255, message = "Product name too long")
        String name,
    @Size(max = 511, message = "Description too long") String description,
    @NotNull(message = "Price is required") @Positive(message = "Price must be greater than zero")
        BigDecimal price,
    @NotNull(message = "Updater User ID is required") UUID updatedBy) {}
