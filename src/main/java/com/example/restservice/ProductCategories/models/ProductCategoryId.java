package com.example.restservice.ProductCategories.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductCategoryId implements Serializable {

  private UUID productId;
  private UUID categoryId;

  protected ProductCategoryId() {}

  public ProductCategoryId(UUID productId, UUID categoryId) {
    this.productId = productId;
    this.categoryId = categoryId;
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
    if (!(o instanceof ProductCategoryId)) return false;
    ProductCategoryId that = (ProductCategoryId) o;
    return Objects.equals(productId, that.productId) && Objects.equals(categoryId, that.categoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, categoryId);
  }
}
