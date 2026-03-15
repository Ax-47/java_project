package com.example.restservice.Products.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.dto.ImagesResponseDTO;
import com.example.restservice.Products.domain.Product;

public record ProductResponseDTO(
    UUID id,
    String name,
    BigDecimal price,
    String description,
    List<ImagesResponseDTO> images,
    UUID createdBy,
    Instant createdAt,
    Instant updatedAt) {

  public static ProductResponseDTO from(Product product, List<Image> images) {
    List<ImagesResponseDTO> imageDtos = images.stream().map(ImagesResponseDTO::from).toList();
    return new ProductResponseDTO(
        product.getId(),
        product.getName(),
        product.getPrice().getValue(),
        product.getDescription(),
        imageDtos,
        product.getCreatedBy(),
        product.getCreatedAt(),
        product.getUpdatedAt());
  }
}
