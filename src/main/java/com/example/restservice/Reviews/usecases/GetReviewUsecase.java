package com.example.restservice.Reviews.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Reviews.domain.DatabaseReviewRepository;
import com.example.restservice.Reviews.domain.Review;
import com.example.restservice.Reviews.dto.ReviewResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class GetReviewUsecase {
  private final DatabaseReviewRepository databaseReviewRepository;

  GetReviewUsecase(DatabaseReviewRepository databaseReviewRepository) {
    this.databaseReviewRepository = databaseReviewRepository;
  }

  @Transactional
  public ReviewResponseDTO execute(UUID reviewId) {
    Review review =
        databaseReviewRepository
            .findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Review not found"));
    return new ReviewResponseDTO(
        review.getId(),
        review.getProductId(),
        review.getUserId(),
        review.getRating(),
        review.getComment());
  }
}
