package com.example.restservice.Reviews.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record DeleteReviewRequestDTO(
    @NotNull(message = "Review ID is required") UUID reviewId,
    // TODO: delete ts use userId from auth intead of ts.
    @NotNull(message = "User ID is required") UUID userId) {}
