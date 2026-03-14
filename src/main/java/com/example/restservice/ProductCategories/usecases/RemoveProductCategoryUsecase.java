package com.example.restservice.ProductCategories.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;
import com.example.restservice.ProductCategories.domain.ProductCategory;

@Service
public class RemoveProductCategoryUsecase {

  private final DatabaseProductCategoryRepository productCategoryRepository;

  public RemoveProductCategoryUsecase(DatabaseProductCategoryRepository productCategoryRepository) {
    this.productCategoryRepository = productCategoryRepository;
  }

  public void execute(UUID productId, UUID categoryId) {

    ProductCategory relation = ProductCategory.of(productId, categoryId);

    productCategoryRepository.delete(relation);
  }
}
