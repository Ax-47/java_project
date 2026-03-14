package com.example.restservice.Products.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateProductRequestDTO(
    @NotBlank(message = "Product name is required")
        @Size(max = 255, message = "Product name too long")
        String name,
    @Size(max = 511, message = "Description too long") String description,
    @NotNull(message = "Category ID is required") UUID categoryId,
    @NotNull(message = "Price is required") @Positive(message = "Price must be greater than zero")
        BigDecimal price,
    @NotNull(message = "Creator User ID is required") UUID createdBy) {}
