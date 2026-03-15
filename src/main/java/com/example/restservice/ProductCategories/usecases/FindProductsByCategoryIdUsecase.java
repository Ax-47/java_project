package com.example.restservice.ProductCategories.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.dto.ProductResponseDTO;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

@Service
public class FindProductsByCategoryIdUsecase {

  private final DatabaseProductCategoryRepository databaseProductCategoryRepository;

  public FindProductsByCategoryIdUsecase(
      DatabaseProductCategoryRepository databaseProductCategoryRepository) {

    this.databaseProductCategoryRepository = databaseProductCategoryRepository;
  }

  public Page<ProductResponseDTO> execute(UUID categoryId, PageQuery query) {
    Page<Product> productsPage =
        databaseProductCategoryRepository.findProductsByCategoryId(categoryId, query);
    List<ProductResponseDTO> content =
        productsPage.content().stream().map(ProductResponseDTO::from).toList();
    return new Page<>(
        content,
        productsPage.totalElements(),
        productsPage.totalPages(),
        productsPage.page(),
        productsPage.size());
  }
}
