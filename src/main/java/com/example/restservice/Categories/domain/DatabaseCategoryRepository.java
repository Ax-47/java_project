package com.example.restservice.Categories.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.restservice.common.*;

public interface DatabaseCategoryRepository {
  public Category save(Category category);

  public Optional<Category> findById(UUID id);

  public List<Category> findAll();

  public int delete(UUID categoryId);

  public Page<Category> findAllCategories(PageQuery query);
}
