package com.example.restservice.Reviews.dto;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateReviewRequestDTO(
    @NotNull(message = "productId is required") UUID productId,
    @NotNull(message = "userId is required") UUID userId,
    @NotNull(message = "rating is required")
        @Min(value = 1, message = "rating must be at least 1")
        @Max(value = 5, message = "rating must be at most 5")
        int rating,
    @NotBlank(message = "comment cannot be blank")
        @Size(max = 500, message = "comment must not exceed 500 characters")
        String comment) {}
