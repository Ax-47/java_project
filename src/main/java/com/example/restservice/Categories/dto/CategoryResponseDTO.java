package com.example.restservice.Categories.dto;

import java.util.UUID;

import com.example.restservice.Categories.domain.Category;

public record CategoryResponseDTO(UUID id, String name) {
  public static CategoryResponseDTO from(Category cate) {
    return new CategoryResponseDTO(
        cate.getId(),
        cate.getCategoryName());
  }

}
