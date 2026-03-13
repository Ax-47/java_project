package com.example.restservice.Categories.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.restservice.Categories.domain.Category;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryModel {

  @Id
  private UUID id;

  @Column(length = 100, nullable = false)
  private String categoryName;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  protected CategoryModel() {
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

  public Category toDomain() {
    return Category.rehydrate(
        this.id,
        this.categoryName,
        this.createdAt,
        this.updatedAt);
  }

  public static CategoryModel fromDomain(Category category) {
    if (category == null) {
      return null;
    }

    CategoryModel entity = new CategoryModel();

    entity.id = category.getId();
    entity.categoryName = category.getCategoryName();

    return entity;
  }
}
