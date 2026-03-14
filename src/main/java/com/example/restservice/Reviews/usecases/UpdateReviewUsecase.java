package com.example.restservice.Reviews.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Reviews.domain.DatabaseReviewRepository;
import com.example.restservice.Reviews.domain.Review;
import com.example.restservice.Reviews.dto.*;

import jakarta.transaction.Transactional;

@Service
public class UpdateReviewUsecase {
  private final DatabaseReviewRepository databaseReviewRepository;

  public UpdateReviewUsecase(DatabaseReviewRepository databaseReviewRepository) {
    this.databaseReviewRepository = databaseReviewRepository;
  }

  @Transactional
  public ReviewResponseDTO execute(UUID reviewId, ReviewRequestDTO request) {
    Review review =
        databaseReviewRepository
            .findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Review not found"));
    review.update(request.rating(), request.comment());
    databaseReviewRepository.save(review);
    return ReviewResponseDTO.from(review);
  }
}
