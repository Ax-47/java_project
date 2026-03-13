package com.example.restservice.ProductCategories.domain;

import java.util.Objects;
import java.util.UUID;

public class ProductCategory {

  private UUID productId;
  private UUID categoryId;

  private ProductCategory(UUID productId, UUID categoryId) {
    this.productId = productId;
    this.categoryId = categoryId;
  }

  public static ProductCategory of(UUID productId, UUID categoryId) {
    return new ProductCategory(productId, categoryId);
  }

  public UUID getProductId() {
    return productId;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProductCategory)) return false;
    ProductCategory that = (ProductCategory) o;
    return productId.equals(that.productId) && categoryId.equals(that.categoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, categoryId);
  }
}
