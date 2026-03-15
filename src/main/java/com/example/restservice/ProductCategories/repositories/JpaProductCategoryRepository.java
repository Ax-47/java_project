package com.example.restservice.ProductCategories.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.restservice.ProductCategories.models.ProductCategoryId;
import com.example.restservice.ProductCategories.models.ProductCategoryModel;
import com.example.restservice.Products.models.ProductModel;

public interface JpaProductCategoryRepository
    extends JpaRepository<ProductCategoryModel, ProductCategoryId> {
  List<ProductCategoryModel> findByProductId(UUID productId);

  @Query(
      """
      SELECT p FROM ProductModel p
      JOIN ProductCategoryModel pc ON pc.productId = p.id
      WHERE pc.categoryId = :categoryId
      """)
  Page<ProductModel> findProductsByCategoryId(UUID categoryId, Pageable pageable);
}
