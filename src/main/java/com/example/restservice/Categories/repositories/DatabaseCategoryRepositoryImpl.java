package com.example.restservice.Categories.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Categories.models.CategoryModel;
import com.example.restservice.common.*;

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

  @Override
  public int delete(UUID categoryId) {
    return jpaCategoryRepository.deleteCategory(categoryId);
  }

  @Override
  public Page<Category> findAllCategories(PageQuery query) {

    Sort sort =
        query.ascending()
            ? Sort.by(query.sortBy()).ascending()
            : Sort.by(query.sortBy()).descending();

    Pageable pageable = PageRequest.of(query.page(), query.size(), sort);

    org.springframework.data.domain.Page<CategoryModel> page =
        jpaCategoryRepository.findAll(pageable);
    List<Category> users = page.getContent().stream().map(category -> category.toDomain()).toList();

    return new Page<>(
        users, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
  }
}
