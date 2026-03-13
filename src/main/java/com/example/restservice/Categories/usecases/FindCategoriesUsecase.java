package com.example.restservice.Categories.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Categories.dto.CategoryResponseDTO;
import com.example.restservice.common.*;

@Service
public class FindCategoriesUsecase {

  private final DatabaseCategoryRepository databaseCategoryRepository;

  public FindCategoriesUsecase(DatabaseCategoryRepository databaseCategoryRepository) {
    this.databaseCategoryRepository = databaseCategoryRepository;
  }

  public Page<CategoryResponseDTO> execute(PageQuery query) {
    Page<Category> usersPage = databaseCategoryRepository.findAllCategories(query);
    List<CategoryResponseDTO> content =
        usersPage.content().stream().map(CategoryResponseDTO::from).toList();
    return new Page<>(
        content,
        usersPage.totalElements(),
        usersPage.totalPages(),
        usersPage.page(),
        usersPage.size());
  }
}
