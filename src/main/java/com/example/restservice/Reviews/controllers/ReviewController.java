package com.example.restservice.Reviews.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.dto.*;
import com.example.restservice.Images.usecases.*;
import com.example.restservice.Reviews.dto.CreateReviewRequestDTO;
import com.example.restservice.Reviews.dto.CreateReviewResponseDTO;
import com.example.restservice.Reviews.dto.ReviewRequestDTO;
import com.example.restservice.Reviews.dto.ReviewResponseDTO;
import com.example.restservice.Reviews.usecases.CreateReviewUsecase;
import com.example.restservice.Reviews.usecases.DeleteReviewUsecase;
import com.example.restservice.Reviews.usecases.FindReveiwByProductUsecase;
import com.example.restservice.Reviews.usecases.FindReveiwByUserUsecase;
import com.example.restservice.Reviews.usecases.FindReviewByProductandIdandUserIdUsecase;
import com.example.restservice.Reviews.usecases.FindReviewUsecase;
import com.example.restservice.Reviews.usecases.GetReviewUsecase;
import com.example.restservice.Reviews.usecases.UpdateReviewUsecase;
import com.example.restservice.common.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

  private final UploadImageUsecase uploadImageUsecase;
  private final FindImageUsecase findImageUsecase;
  private final ReorderImageUsecase reorderImageUsecase;
  private final DeleteImageUsecase deleteImageUsecase;
  private final GetReviewUsecase getReviewUsecase;
  private final FindReviewUsecase findReviewsUsecase;
  private final DeleteReviewUsecase deleteReviewUsecase;
  private final UpdateReviewUsecase updateReviewUsecase;
  private final FindReveiwByUserUsecase findReveiwsByUserUsecase;
  private final FindReveiwByProductUsecase findReveiwsByProductUsecase;
  private final FindReviewByProductandIdandUserIdUsecase findReviewByProductIdandUserIdUsecase;
  private final CreateReviewUsecase createReviewUsecase;

  public ReviewController(
      UploadImageUsecase uploadImageUsecase,
      FindImageUsecase findImageUsecase,
      DeleteImageUsecase deleteImageUsecase,
      ReorderImageUsecase reorderImageUsecase,
      GetReviewUsecase getReviewUsecase,
      FindReviewUsecase findReviewsUsecase,
      DeleteReviewUsecase deleteReviewUsecase,
      UpdateReviewUsecase updateReviewUsecase,
      FindReveiwByUserUsecase findReveiwsByUserUsecase,
      FindReveiwByProductUsecase findReveiwsByProductUsecase,
      FindReviewByProductandIdandUserIdUsecase findReviewByProductIdandUserIdUsecase,
      CreateReviewUsecase createReviewUsecase) {
    this.uploadImageUsecase = uploadImageUsecase;
    this.findImageUsecase = findImageUsecase;
    this.deleteImageUsecase = deleteImageUsecase;
    this.reorderImageUsecase = reorderImageUsecase;
    this.getReviewUsecase = getReviewUsecase;
    this.findReviewsUsecase = findReviewsUsecase;
    this.deleteReviewUsecase = deleteReviewUsecase;
    this.updateReviewUsecase = updateReviewUsecase;
    this.findReveiwsByUserUsecase = findReveiwsByUserUsecase;
    this.findReveiwsByProductUsecase = findReveiwsByProductUsecase;
    this.findReviewByProductIdandUserIdUsecase = findReviewByProductIdandUserIdUsecase;
    this.createReviewUsecase = createReviewUsecase;
  }

  @PostMapping("/{reviewId}/images")
  public UploadImageResponseDTO uploadReviewImage(
      @PathVariable UUID productId, @RequestParam MultipartFile file, @RequestParam int sortOrder)
      throws IOException {

    return uploadImageUsecase.execute(file, productId, ImageResourceType.REVIEW, sortOrder);
  }

  @PostMapping
  public CreateReviewResponseDTO CreateReview(@RequestBody CreateReviewRequestDTO request) {

    return createReviewUsecase.execute(request);
  }

  @GetMapping("/{reviewId}/images")
  public List<UploadImageResponseDTO> findreviewImages(@PathVariable UUID reviewId) {
    return findImageUsecase.execute(reviewId, ImageResourceType.REVIEW);
  }

  @PatchMapping("/{reviewId}/images/reorder")
  public void reorderReviewImages(
      @PathVariable UUID reviewId, @RequestBody List<ReorderImageRequestDTO> request) {

    reorderImageUsecase.execute(reviewId, request);
  }

  @DeleteMapping("/{reviewId}/images/{imageId}")
  public void deleteReviewImage(@PathVariable UUID reviewId, @PathVariable UUID imageId) {

    deleteImageUsecase.execute(reviewId, imageId);
  }

  @GetMapping
  public ResponseEntity<PageResponse<ReviewResponseDTO>> findAllReveiws(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "rating") String sortBy,
      @RequestParam(defaultValue = "true") boolean asc) {

    PageQuery query = new PageQuery(page, size, sortBy, asc);
    return ResponseEntity.ok(PageResponse.from(findReviewsUsecase.execute(query)));
  }

  @GetMapping("/{reviewId}")
  public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable UUID reviewId) {
    return ResponseEntity.ok(getReviewUsecase.execute(reviewId));
  }

  @PutMapping("/{reviewId}")
  public ResponseEntity<ReviewResponseDTO> updateReview(
      @PathVariable UUID reviewId, @Valid @RequestBody ReviewRequestDTO request) {

    return ResponseEntity.ok(updateReviewUsecase.execute(reviewId, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {

    deleteReviewUsecase.execute(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<PageResponse<ReviewResponseDTO>> findReveiwsByUser(
      @PathVariable UUID userId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "rating") String sortBy,
      @RequestParam(defaultValue = "true") boolean asc) {

    PageQuery query = new PageQuery(page, size, sortBy, asc);
    return ResponseEntity.ok(PageResponse.from(findReveiwsByUserUsecase.execute(userId, query)));
  }

  @GetMapping("/products/{productId}")
  public ResponseEntity<PageResponse<ReviewResponseDTO>> findReveiwsByProduct(
      @PathVariable UUID productId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "rating") String sortBy,
      @RequestParam(defaultValue = "true") boolean asc) {

    PageQuery query = new PageQuery(page, size, sortBy, asc);
    return ResponseEntity.ok(
        PageResponse.from(findReveiwsByProductUsecase.execute(productId, query)));
  }

  @GetMapping("/products/{productId}/users/{userId}")
  public ResponseEntity<ReviewResponseDTO> findProductIdandUserId(
      @PathVariable UUID productId, @PathVariable UUID userId) {
    return ResponseEntity.ok(findReviewByProductIdandUserIdUsecase.execute(productId, userId));
  }
}
