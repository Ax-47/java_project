package com.example.restservice.ProductCategories.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Categories.dto.CategoryResponseDTO;
import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;

@Service
public class FindProductCategoriesUsecase {

  private final DatabaseProductCategoryRepository productCategoryRepository;

  public FindProductCategoriesUsecase(DatabaseProductCategoryRepository productCategoryRepository) {
    this.productCategoryRepository = productCategoryRepository;
  }

  public List<CategoryResponseDTO> execute(UUID productId) {

    return productCategoryRepository.findCategoriesByProductId(productId).stream()
        .map(CategoryResponseDTO::from)
        .toList();
  }
}
