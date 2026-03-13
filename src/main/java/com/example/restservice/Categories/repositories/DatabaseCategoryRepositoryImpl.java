package com.example.restservice.Categories.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Categories.models.CategoryModel;

@Repository
public class DatabaseCategoryRepositoryImpl implements DatabaseCategoryRepository {
  private final JpaCategoryRepository jpaCategoryRepository;

  public DatabaseCategoryRepositoryImpl(JpaCategoryRepository jpaCategoryRepository) {
    this.jpaCategoryRepository = jpaCategoryRepository;
  }

  @Override
  public Category save(Category category) {
    CategoryModel model = CategoryModel.fromDomain(category);
    CategoryModel saved = jpaCategoryRepository.save(model);
    return saved.toDomain();
  }

  @Override
  public Optional<Category> findById(UUID id) {
    return jpaCategoryRepository.findById(id).map(CategoryModel::toDomain);
  }
}
