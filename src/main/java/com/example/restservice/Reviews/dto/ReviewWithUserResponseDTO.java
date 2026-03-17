package com.example.restservice.Reviews.dto;

import java.util.UUID;

import com.example.restservice.Reviews.domain.Review;

public record ReviewWithUserResponseDTO(
    UUID id,
    UUID productId,
    UUID userId,
    String username,
    String profileUrl,
    int rating,
    String comment) {
  public static ReviewWithUserResponseDTO from(Review review, String username, String profileUrl) {
    return new ReviewWithUserResponseDTO(
        review.getId(),
        review.getProductId(),
        review.getUserId(),
        username,
        profileUrl,
        review.getRating(),
        review.getComment());
  }
}
