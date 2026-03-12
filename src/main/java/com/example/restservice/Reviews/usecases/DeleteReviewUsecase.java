package com.example.restservice.Reviews.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.Reviews.domain.*;
import com.example.restservice.Reviews.dto.*;
import com.example.restservice.Reviews.domain.DatabaseReviewRepository;

@Service
public class DeleteReviewUsecase {

    private final DatabaseReviewRepository databaseReviewRepository;

    public DeleteReviewUsecase(DatabaseReviewRepository databaseReviewRepository) {
        this.databaseReviewRepository = databaseReviewRepository;
    }

    public DeleteReviewResponseDTO execute(DeleteReviewRequestDTO request) {
        Review existingReview = this.databaseReviewRepository.findById(request.reviewId())
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!existingReview.getUserId().equals(request.userId())) {
            throw new RuntimeException("Unauthorized to delete this review");
        }
        this.databaseReviewRepository.delete(existingReview);

        return new DeleteReviewResponseDTO("Review was deleted successfully");
    }
}
