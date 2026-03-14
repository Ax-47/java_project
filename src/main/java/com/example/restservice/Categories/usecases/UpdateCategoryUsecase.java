package com.example.restservice.Categories.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Categories.dto.*;

import jakarta.transaction.Transactional;

@Service
public class UpdateCategoryUsecase {
  private final DatabaseCategoryRepository databaseCategoryRepository;

  UpdateCategoryUsecase(DatabaseCategoryRepository databaseCategoryRepository) {
    this.databaseCategoryRepository = databaseCategoryRepository;
  }

  @Transactional
  public CategoryResponseDTO execute(UUID categoryId, CategoryRequestDTO request) {
    Category category =
        databaseCategoryRepository
            .findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    category.rename(request.name());
    databaseCategoryRepository.save(category);
    return new CategoryResponseDTO(category.getId(), category.getCategoryName());
  }
}
