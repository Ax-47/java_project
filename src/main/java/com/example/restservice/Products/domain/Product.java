package com.example.restservice.Products.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {

  private final Long id;
  private String name;
  private double price;
  private String description;
  private final Long createdBy;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Product(
      Long id,
      String name,
      double price,
      String description,
      Long createdBy,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    validateName(name);
    validatePrice(price);
    validateDescription(description);

    this.id = Objects.requireNonNull(id);
    this.name = name;
    this.price = price;
    this.description = description;
    this.createdBy = Objects.requireNonNull(createdBy);
    this.createdAt = Objects.requireNonNull(createdAt);
    this.updatedAt = Objects.requireNonNull(updatedAt);
  }

  public static Product create(
      Long id,
      String name,
      double price,
      String description,
      Long createdBy) {
    return new Product(
        id,
        name,
        price,
        description,
        createdBy,
        LocalDateTime.now(),
        LocalDateTime.now());
  }

  public static Product rehydrate(
      Long id,
      String name,
      double price,
      String description,
      Long createdBy,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    return new Product(
        id,
        name,
        price,
        description,
        createdBy,
        createdAt,
        updatedAt);
  }

  public void update(String name, double price, String description) {
    validateName(name);
    validatePrice(price);
    validateDescription(description);

    this.name = name;
    this.price = price;
    this.description = description;
    this.updatedAt = LocalDateTime.now();
  }

  private void validateName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Product name is required");
    }
    if (name.length() > 255) {
      throw new IllegalArgumentException("Product name too long");
    }
  }

  private void validatePrice(double price) {
    if (price <= 0) {
      throw new IllegalArgumentException("Price must be greater than zero");
    }
  }

  private void validateDescription(String description) {
    if (description != null && description.length() > 511) {
      throw new IllegalArgumentException("Description too long");
    }
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public String getDescription() {
    return description;
  }

  public Long getCreatedBy() {
    return createdBy;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
