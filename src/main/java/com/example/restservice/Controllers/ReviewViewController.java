package com.example.restservice.Controllers;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.usecases.UploadImageUsecase;
import com.example.restservice.Reviews.dto.CreateReviewRequestDTO;
import com.example.restservice.Reviews.dto.ReviewRequestDTO;
import com.example.restservice.Reviews.usecases.CreateReviewUsecase;
import com.example.restservice.Reviews.usecases.DeleteReviewUsecase;
import com.example.restservice.Reviews.usecases.UpdateReviewUsecase;

@Controller
@RequestMapping("/review")
public class ReviewViewController {
  private final CreateReviewUsecase createReviewUsecase;
  private final UpdateReviewUsecase updateReviewUsecase;
  private final DeleteReviewUsecase deleteReviewUsecase;
  private final UploadImageUsecase uploadImageUsecase;

  public ReviewViewController(
      CreateReviewUsecase createReviewUsecase,
      UpdateReviewUsecase updateReviewUsecase,
      DeleteReviewUsecase deleteReviewUsecase,
      UploadImageUsecase uploadImageUsecase) {
    this.createReviewUsecase = createReviewUsecase;
    this.updateReviewUsecase = updateReviewUsecase;
    this.deleteReviewUsecase = deleteReviewUsecase;
    this.uploadImageUsecase = uploadImageUsecase;
  }

  private void handleImageUpload(MultipartFile[] files, UUID reviewId) throws Exception {
    if (files != null && files.length > 0) {
      int i = 1;
      for (MultipartFile file : files) {
        if (!file.isEmpty()) {
          uploadImageUsecase.execute(file, reviewId, ImageResourceType.REVIEW, i++);
        }
      }
    }
  }

  @PostMapping
  public String createReview(
      @RequestParam("productId") UUID productId,
      @RequestParam("rating") int rating,
      @RequestParam("comment") String comment,
      @RequestParam(value = "files", required = false) MultipartFile[] files,
      @AuthenticationPrincipal UserPrincipalDTO user) {

    var res =
        createReviewUsecase.execute(
            new CreateReviewRequestDTO(productId, user.id(), rating, comment));
    try {
      handleImageUpload(files, res.id());
    } catch (Exception e) {
      return "redirect:/products/" + productId + "?success=true&imageUploadFailed=true";
    }
    return "redirect:/products/" + productId + "?success=true";
  }

  @PutMapping("/{reviewId}")
  public String updateReview(
      @PathVariable UUID reviewId,
      @RequestParam("productId") UUID productId,
      @RequestParam("rating") int rating,
      @RequestParam("comment") String comment,
      @RequestParam(value = "files", required = false) MultipartFile[] files,
      @AuthenticationPrincipal UserPrincipalDTO user) {

    updateReviewUsecase.execute(reviewId, new ReviewRequestDTO(rating, comment));
    try {
      handleImageUpload(files, reviewId);
    } catch (Exception e) {
      return "redirect:/products/" + productId + "?success=true&imageUploadFailed=true";
    }
    return "redirect:/products/" + productId + "?success=true";
  }

  @DeleteMapping("/{reviewId}")
  public String deleteReview(
      @PathVariable UUID reviewId,
      @RequestParam("productId") UUID productId,
      @AuthenticationPrincipal UserPrincipalDTO user) {
    deleteReviewUsecase.execute(reviewId);
    return "redirect:/products/" + productId + "?success=true";
  }
}
