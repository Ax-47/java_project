package com.example.restservice.Reviews.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Address.dto.CreateAddressRequestDTO;
import com.example.restservice.Address.dto.CreateAddressResponseDTO;
import com.example.restservice.Reviews.dto.CreateReviewRequestDTO;
import com.example.restservice.Reviews.dto.CreateReviewResponseDTO;
import com.example.restservice.Reviews.usecases.CreateReviewUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

  private final CreateReviewUsecase createReviewUsecase;

  public ReviewController(CreateReviewUsecase createReviewUsecase) {
    this.createReviewUsecase = createReviewUsecase;
  }

  @PostMapping
  public ResponseEntity<CreateReviewResponseDTO> create(
      @Valid @RequestBody CreateReviewRequestDTO requestModel) {

    CreateReviewResponseDTO response = createReviewUsecase.execute(requestModel);

    return ResponseEntity.ok(response);
  }
}
