package com.example.restservice.Reviews.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.restservice.Reviews.domain.Review;

@Entity
@Table(name = "reviews", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "product_id", "user_id" })
})
public class ReviewModel {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "product_id", nullable = false)
  private UUID productId;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "rating", nullable = false)
  private int rating;

  @Column(name = "comment", nullable = false)
  private String comment;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  protected ReviewModel() {
    // JPA ต้องการ constructor เปล่า
  }

  public ReviewModel(
      UUID id,
      UUID productId,
      UUID userId,
      int rating,
      String comment,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.productId = productId;
    this.userId = userId;
    this.rating = rating;
    this.comment = comment;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UUID getId() {
    return id;
  }

  public UUID getProductId() {
    return productId;
  }

  public UUID getUserId() {
    return userId;
  }

  public int getRating() {
    return rating;
  }

  public String getComment() {
    return comment;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public static ReviewModel toModel(Review review) {
    return new ReviewModel(
        review.getId(),
        review.getProductId(),
        review.getUserId(),
        review.getRating(),
        review.getComment(),
        review.getCreatedAt(),
        review.getUpdatedAt());
  }

  public static Review toDomain(ReviewModel model) {
    return Review.rehydrate(
        model.getId(),
        model.getProductId(),
        model.getUserId(),
        model.getRating(),
        model.getComment(),
        model.getCreatedAt(),
        model.getUpdatedAt());
  }
}
