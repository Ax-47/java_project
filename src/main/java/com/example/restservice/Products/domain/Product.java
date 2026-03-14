package com.example.restservice.Products.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.example.restservice.ProductCategories.domain.ProductCategory;
import com.example.restservice.Products.exceptions.*;

public class Product {

  private final UUID id;
  private String name;
  private Price price;
  private String description;
  private final Set<ProductCategory> categories = new HashSet<>();
  private final UUID createdBy;
  private final Instant createdAt;
  private Instant updatedAt;

  private Product(
      UUID id,
      String name,
      Price price,
      String description,
      UUID createdBy,
      Instant createdAt,
      Instant updatedAt) {

    validateName(name);
    validateDescription(description);

    this.id = Objects.requireNonNull(id);
    this.name = name;
    this.price = Objects.requireNonNull(price);
    this.description = description;
    this.createdBy = Objects.requireNonNull(createdBy);
    this.createdAt = Objects.requireNonNull(createdAt);
    this.updatedAt = Objects.requireNonNull(updatedAt);
  }

  public static Product create(String name, Price price, String description, UUID createdBy) {

    Instant now = Instant.now();
    return new Product(UUID.randomUUID(), name, price, description, createdBy, now, now);
  }

  public static Product rehydrate(
      UUID id,
      String name,
      Price price,
      String description,
      UUID createdBy,
      UUID categoryId,
      Instant createdAt,
      Instant updatedAt) {

    return new Product(id, name, price, description, createdBy, createdAt, updatedAt);
  }

  public void update(String name, BigDecimal price, String description) {
    validateName(name);
    validateDescription(description);

    this.name = name;
    this.price = Price.of(price);
    this.description = description;
    this.updatedAt = Instant.now();
  }

  private void validateName(String name) {
    if (name == null || name.isBlank()) {
      throw new InvalidProductNameException("Product name is required");
    }
    if (name.length() > 255) {
      throw new InvalidProductNameException("Product name too long");
    }
  }

  private void validateDescription(String description) {
    if (description != null && description.length() > 511) {
      throw new InvalidProductDescriptionException("Description too long");
    }
  }

  public void addCategory(UUID categoryId) {
    ProductCategory relation = ProductCategory.of(this.id, categoryId);
    if (!categories.add(relation)) {
      throw new IllegalStateException("Category already exists");
    }
  }

  public void removeCategory(UUID categoryId) {
    categories.removeIf(pc -> pc.getCategoryId().equals(categoryId));
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Price getPrice() {
    return price;
  }

  public String getDescription() {
    return description;
  }

  public UUID getCreatedBy() {
    return createdBy;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

}
