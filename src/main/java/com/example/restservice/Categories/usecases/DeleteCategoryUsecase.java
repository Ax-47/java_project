package com.example.restservice.Categories.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Categories.dto.*;

import jakarta.transaction.Transactional;

@Service
public class DeleteCategoryUsecase {
  private final DatabaseCategoryRepository databaseCategoryRepository;

  DeleteCategoryUsecase(DatabaseCategoryRepository databaseCategoryRepository) {
    this.databaseCategoryRepository = databaseCategoryRepository;
  }

  @Transactional
  public void execute(UUID categoryId) {
    databaseCategoryRepository.delete(categoryId);
  }
}
