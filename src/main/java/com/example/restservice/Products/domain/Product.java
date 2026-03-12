package com.example.restservice.Products.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.example.restservice.Products.exceptions.*;

public class Product {

  private final UUID id;
  private String name;
  private Price price;
  private String description;
  private final UUID createdBy;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Product(
      UUID id,
      String name,
      Price price,
      String description,
      UUID createdBy,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {

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

  public static Product create(
      UUID id, String name, BigDecimal price, String description, UUID createdBy) {

    return new Product(
        id,
        name,
        Price.of(price),
        description,
        createdBy,
        LocalDateTime.now(),
        LocalDateTime.now());
  }

  public static Product rehydrate(
      UUID id,
      String name,
      BigDecimal price,
      String description,
      UUID createdBy,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {

    return new Product(id, name, Price.of(price), description, createdBy, createdAt, updatedAt);
  }

  public void update(String name, BigDecimal price, String description) {
    validateName(name);
    validateDescription(description);

    this.name = name;
    this.price = Price.of(price);
    this.description = description;
    this.updatedAt = LocalDateTime.now();
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
