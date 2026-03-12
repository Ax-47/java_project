package com.example.restservice.Reviews.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {
  private UUID id;

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

  private UUID productId;
  private UUID userId;
  private int rating;
  private String comment;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Review(
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

  public static Review create(UUID productId, UUID userId, int rating, String comment) {

    if (rating < 1 || rating > 5) {
      throw new IllegalArgumentException("rating must be 1-5");
    }

    LocalDateTime now = LocalDateTime.now();

    return new Review(UUID.randomUUID(), productId, userId, rating, comment, now, now);
  }

  public static Review rehydrate(
      UUID id,
      UUID productId,
      UUID userId,
      int rating,
      String comment,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    return new Review(id, productId, userId, rating, comment, createdAt, updatedAt);
  }

  public void update(int rating, String comment) {

    if (rating < 1 || rating > 5) {
      throw new IllegalArgumentException("rating must be 1-5");
    }

    this.rating = rating;
    this.comment = comment;
    this.updatedAt = LocalDateTime.now();
  }
}
