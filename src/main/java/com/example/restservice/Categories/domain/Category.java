package com.example.restservice.Categories.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Category {

  private final UUID id;
  private String categoryName;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Category(UUID id, String categoryName, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = Objects.requireNonNull(id);
    this.categoryName = validate(categoryName);
    this.createdAt = Objects.requireNonNull(createdAt);
    this.updatedAt = Objects.requireNonNull(updatedAt);
  }

  public static Category create(String categoryName) {
    LocalDateTime now = LocalDateTime.now();

    return new Category(UUID.randomUUID(), categoryName, now, now);
  }

  public static Category rehydrate(
      UUID id, String categoryName, LocalDateTime createdAt, LocalDateTime updatedAt) {
    return new Category(id, categoryName, createdAt, updatedAt);
  }

  public void rename(String categoryName) {
    this.categoryName = validate(categoryName);
    this.updatedAt = LocalDateTime.now();
  }

  private String validate(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Category name required");
    }
    return name;
  }

  public UUID getId() {
    return id;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
