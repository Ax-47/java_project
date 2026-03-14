package com.example.restservice.Reviews.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Reviews.domain.DatabaseReviewRepository;
import com.example.restservice.Reviews.domain.Review;
import com.example.restservice.Reviews.dto.ReviewResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class FindReviewByProductandIdandUserIdUsecase {

  private final DatabaseReviewRepository databaseReviewRepository;

  public FindReviewByProductandIdandUserIdUsecase(
      DatabaseReviewRepository databaseReviewRepository) {
    this.databaseReviewRepository = databaseReviewRepository;
  }

  @Transactional
  public ReviewResponseDTO execute(UUID userId, UUID productId) {

    Review review =
        databaseReviewRepository
            .findReviewByUserIdAndProductId(userId, productId)
            .orElseThrow(() -> new RuntimeException("Review not found"));
    ;
    return new ReviewResponseDTO(
        review.getId(),
        review.getProductId(),
        review.getUserId(),
        review.getRating(),
        review.getComment());
  }
}
