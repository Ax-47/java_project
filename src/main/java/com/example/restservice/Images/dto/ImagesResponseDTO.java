package com.example.restservice.Images.dto;

import java.time.Instant;
import java.util.UUID;

import com.example.restservice.Images.domain.Image;

public record ImagesResponseDTO(UUID id, int sortOrder, Instant createdAt) {

  public static ImagesResponseDTO from(Image image) {
    return new ImagesResponseDTO(image.getId(), image.getSortOrder(), image.getCreatedAt());
  }
}
