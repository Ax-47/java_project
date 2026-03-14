package com.example.restservice.ProductCategories.models;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Categories.models.CategoryModel;
import com.example.restservice.ProductCategories.domain.ProductCategory;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.models.ProductModel;

import jakarta.persistence.*;

@Entity
@Table(name = "product_categories")
public class ProductCategoryModel {

  @EmbeddedId
  private ProductCategoryId id;

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  private ProductModel product;

  @ManyToOne
  @MapsId("categoryId")
  @JoinColumn(name = "category_id")
  private CategoryModel category;

  protected ProductCategoryModel() {
  }

  public ProductCategoryModel(ProductModel product, CategoryModel category) {
    this.product = product;
    this.category = category;
    this.id = new ProductCategoryId(product.getId(), category.getId());
  }

  public ProductCategory toDomain() {
    return ProductCategory.of(this.id.getProductId(), this.id.getCategoryId());
  }

  public ProductCategoryId getId() {
    return id;
  }

  public ProductModel getProduct() {
    return product;
  }

  public CategoryModel getCategory() {
    return category;
  }
}
