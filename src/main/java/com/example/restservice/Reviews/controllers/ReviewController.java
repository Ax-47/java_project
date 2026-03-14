package com.example.restservice.Reviews.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.dto.*;
import com.example.restservice.Images.usecases.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

  private final UploadImageUsecase uploadImageUsecase;
  private final FindImageUsecase findImageUsecase;
  private final ReorderImageUsecase reorderImageUsecase;
  private final DeleteImageUsecase deleteImageUsecase;

  public ReviewController(
      UploadImageUsecase uploadImageUsecase,
      FindImageUsecase findImageUsecase,
      DeleteImageUsecase deleteImageUsecase,
      ReorderImageUsecase reorderImageUsecase) {
    this.uploadImageUsecase = uploadImageUsecase;
    this.findImageUsecase = findImageUsecase;
    this.deleteImageUsecase = deleteImageUsecase;
    this.reorderImageUsecase = reorderImageUsecase;
  }

  @PostMapping("/api/reviews/{reviewId}/images")
  public UploadImageResponseDTO uploadReviewImage(
      @PathVariable UUID productId, @RequestParam MultipartFile file, @RequestParam int sortOrder)
      throws IOException {

    return uploadImageUsecase.execute(file, productId, ImageResourceType.REVIEW, sortOrder);
  }

  @GetMapping("/api/reviews/{reviewId}/images")
  public List<UploadImageResponseDTO> findreviewImages(@PathVariable UUID reviewId) {
    return findImageUsecase.execute(reviewId, ImageResourceType.REVIEW);
  }

  @PatchMapping("/api/reviews/{reviewId}/images/reorder")
  public void reorderReviewImages(
      @PathVariable UUID reviewId, @RequestBody List<ReorderImageRequestDTO> request) {

    reorderImageUsecase.execute(reviewId, request);
  }

  @DeleteMapping("/{reviewId}/images/{imageId}")
  public void deleteReviewImage(@PathVariable UUID reviewId, @PathVariable UUID imageId) {

    deleteImageUsecase.execute(reviewId, imageId);
  }
  // GET /api/review
  // GET /api/review/products/{productId}
  // GET /api/review/{reviewId}
  // GET /api/review/users/{userId}
  // GET /api/review/products/{productId}/users/{userId}
  // PUT /api/review/{reviewId}
  // DELETE /api/review/{reviewId}
}
