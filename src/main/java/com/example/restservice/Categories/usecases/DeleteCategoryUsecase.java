package com.example.restservice.Categories.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class DeleteCategoryUsecase {
  private final DatabaseCategoryRepository databaseCategoryRepository;

  private final DatabaseProductCategoryRepository databaseProductCategoryRepository;

  DeleteCategoryUsecase(
      DatabaseCategoryRepository databaseCategoryRepository,
      DatabaseProductCategoryRepository databaseProductCategoryRepository) {
    this.databaseCategoryRepository = databaseCategoryRepository;
    this.databaseProductCategoryRepository = databaseProductCategoryRepository;
  }

  @Transactional
  public void execute(UUID categoryId) {
    databaseProductCategoryRepository.deleteByCategoryId(categoryId);
    databaseCategoryRepository.delete(categoryId);
  }
}
