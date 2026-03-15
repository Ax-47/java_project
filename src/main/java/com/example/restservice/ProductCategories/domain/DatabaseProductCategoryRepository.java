package com.example.restservice.ProductCategories.domain;

import java.util.List;
import java.util.UUID;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.common.*;

public interface DatabaseProductCategoryRepository {

  public ProductCategory save(ProductCategory relation);

  public void delete(ProductCategory relation);

  List<Category> findCategoriesByProductId(UUID productId);

  public Page<Product> findProductsByCategoryId(UUID categoryId, PageQuery query);
}
