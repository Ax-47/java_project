package com.example.restservice.ProductCategories.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restservice.ProductCategories.models.ProductCategoryId;
import com.example.restservice.ProductCategories.models.ProductCategoryModel;
import com.example.restservice.Products.models.ProductModel;

public interface JpaProductCategoryRepository
    extends JpaRepository<ProductCategoryModel, ProductCategoryId> {
  List<ProductCategoryModel> findByProductId(UUID productId);

  @Query(
      """
      SELECT pc.product
      FROM ProductCategoryModel pc
      WHERE pc.category.id = :categoryId
      """)
  Page<ProductModel> findProductsByCategoryId(UUID categoryId, Pageable pageable);

  @Query(
      """
      SELECT pc
      FROM ProductCategoryModel pc
      JOIN FETCH pc.product
      WHERE pc.category.id IN :categoryIds
      """)
  Page<ProductCategoryModel> findByCategoryIdIn(
      @Param("categoryIds") List<UUID> categoryIds, Pageable pageable);
}
