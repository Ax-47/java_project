package com.example.restservice.Categories.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Categories.dto.*;

import jakarta.transaction.Transactional;

@Service
public class CreateCategoryUsecase {
  private final DatabaseCategoryRepository databaseCategoryRepository;

  CreateCategoryUsecase(DatabaseCategoryRepository databaseCategoryRepository) {
    this.databaseCategoryRepository = databaseCategoryRepository;
  }

  @Transactional
  public CategoryResponseDTO execute(CategoryRequestDTO request) {

    Category newCategory = Category.create(request.name());
    Category savedCategory = databaseCategoryRepository.save(newCategory);
    return new CategoryResponseDTO(savedCategory.getId(), savedCategory.getCategoryName());
  }
}
