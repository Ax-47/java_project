package com.example.restservice.Reviews.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Reviews.domain.*;
import com.example.restservice.Reviews.dto.*;

import jakarta.transaction.Transactional;

@Service
public class DeleteReviewUsecase {

  private final DatabaseReviewRepository databaseReviewRepository;

  public DeleteReviewUsecase(DatabaseReviewRepository databaseReviewRepository) {
    this.databaseReviewRepository = databaseReviewRepository;
  }

  @Transactional
  public DeleteReviewResponseDTO execute(UUID reviewId) {
    Review existingReview =
        this.databaseReviewRepository
            .findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Review not found"));

    this.databaseReviewRepository.delete(existingReview);

    return new DeleteReviewResponseDTO("Review was deleted successfully");
  }
}
