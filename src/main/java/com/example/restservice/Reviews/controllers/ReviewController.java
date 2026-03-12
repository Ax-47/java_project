package com.example.restservice.Reviews.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Reviews.dto.CreateReviewRequestDTO;
import com.example.restservice.Reviews.dto.CreateReviewResponseDTO;
import com.example.restservice.Reviews.dto.DeleteReviewRequestDTO;
import com.example.restservice.Reviews.dto.DeleteReviewResponseDTO;
import com.example.restservice.Reviews.usecases.CreateReviewUsecase;
import com.example.restservice.Reviews.usecases.DeleteReviewUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

  private final CreateReviewUsecase createReviewUsecase;
  private final DeleteReviewUsecase deleteReviewUsecase;

  public ReviewController(CreateReviewUsecase createReviewUsecase, DeleteReviewUsecase deleteReviewUsecase) {
    this.createReviewUsecase = createReviewUsecase;
    this.deleteReviewUsecase = deleteReviewUsecase;
  }

  @PostMapping
  public ResponseEntity<CreateReviewResponseDTO> create(
      @Valid @RequestBody CreateReviewRequestDTO requestModel) {

    CreateReviewResponseDTO response = createReviewUsecase.execute(requestModel);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping
  public ResponseEntity<DeleteReviewResponseDTO> delete(
      @Valid @RequestBody DeleteReviewRequestDTO requestModel) {

    DeleteReviewResponseDTO response = deleteReviewUsecase.execute(requestModel);

    return ResponseEntity.ok(response);
  }
}
