package com.example.restservice.Categories.domain;

import java.util.Optional;
import java.util.UUID;

import com.example.restservice.common.*;

public interface DatabaseCategoryRepository {
  public Category save(Category category);

  public Optional<Category> findById(UUID id);

  public int delete(UUID categoryId);

  public Page<Category> findAllCategories(PageQuery query);
}
