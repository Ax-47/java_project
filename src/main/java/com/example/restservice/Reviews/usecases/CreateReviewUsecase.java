package com.example.restservice.Reviews.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.Reviews.domain.*;
import com.example.restservice.Reviews.dto.*;

@Service
public class CreateReviewUsecase {

  private final DatabaseReviewRepository databaseReviewRepository;

  public CreateReviewUsecase(DatabaseReviewRepository databaseReviewRepository) {
    this.databaseReviewRepository = databaseReviewRepository;
  }

  public CreateReviewResponseDTO execute(CreateReviewRequestDTO request) {
    Review newReview =
        Review.create(request.productId(), request.userId(), request.rating(), request.comment());
    this.databaseReviewRepository.save(newReview);
    return new CreateReviewResponseDTO(newReview.getId(), "Review was created");
  }
}
