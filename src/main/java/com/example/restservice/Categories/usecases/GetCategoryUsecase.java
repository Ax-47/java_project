package com.example.restservice.Categories.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Categories.dto.*;

import jakarta.transaction.Transactional;

@Service
public class GetCategoryUsecase {
  private final DatabaseCategoryRepository databaseCategoryRepository;

  GetCategoryUsecase(DatabaseCategoryRepository databaseCategoryRepository) {
    this.databaseCategoryRepository = databaseCategoryRepository;
  }

  @Transactional
  public CategoryResponseDTO execute(UUID categoryId) {
    Category category =
        databaseCategoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    return new CategoryResponseDTO(category.getId(), category.getCategoryName());
  }
}
