package com.example.restservice.Reviews.dto;

import java.util.UUID;

import com.example.restservice.Reviews.domain.Review;

public record ReviewResponseDTO(UUID id, UUID productId, UUID userId, int rating, String comment) {
  public static ReviewResponseDTO from(Review review) {
    return new ReviewResponseDTO(
        review.getId(),
        review.getProductId(),
        review.getUserId(),
        review.getRating(),
        review.getComment());
  }
}
