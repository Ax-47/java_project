package com.example.restservice.Products.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.example.restservice.Products.domain.Product;

public record ProductResponseDTO(
    UUID id,
    String name,
    BigDecimal price,
    String description,
    UUID createdBy,
    Instant createdAt,
    Instant updatedAt) {

  public static ProductResponseDTO from(Product product) {
    return new ProductResponseDTO(
        product.getId(),
        product.getName(),
        product.getPrice().getValue(),
        product.getDescription(),
        product.getCreatedBy(),
        product.getCreatedAt(),
        product.getUpdatedAt());
  }
}
