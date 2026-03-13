package com.example.restservice.ProductCategories.domain;

import java.util.UUID;

public class ProductCategory {

  private UUID productId;
  private UUID categoryId;

  private ProductCategory(UUID productId, UUID categoryId) {
    this.productId = productId;
    this.categoryId = categoryId;
  }

  public static ProductCategory create(UUID productId, UUID categoryId) {
    return new ProductCategory(productId, categoryId);
  }

  public UUID getProductId() {
    return productId;
  }

  public UUID getCategoryId() {
    return categoryId;
  }
}
