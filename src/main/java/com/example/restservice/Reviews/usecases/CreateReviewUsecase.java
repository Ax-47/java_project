package com.example.restservice.Reviews.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.Reviews.domain.*;
import com.example.restservice.Reviews.dto.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Service
public class CreateReviewUsecase {

        private final DatabaseReviewRepository databaseReviewRepository;

        public CreateReviewUsecase(DatabaseReviewRepository databaseReviewRepository) {
                this.databaseReviewRepository = databaseReviewRepository;
        }
// @NotNull(message = "productId is required")
//         UUID productId,

//         @NotNull(message = "userId is required")
//         UUID userId,

//         @NotNull(message = "rating is required")
//         @Min(value = 1, message = "rating must be at least 1")
//         @Max(value = 5, message = "rating must be at most 5")
//         int rating,

//         @NotBlank(message = "comment cannot be blank")
//         @Size(max = 500, message = "comment must not exceed 500 characters")
//         String comment

        public CreateReviewResponseDTO execute(CreateReviewRequestDTO request) {
                Review newReview = Review.create(
                                request.userId(),
                                request.productId(),
                                request.rating(),
                                request.comment());
                this.databaseReviewRepository.save(newReview);
                return new CreateReviewResponseDTO("Review was created");
        }
}
