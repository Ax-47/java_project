package com.example.restservice.ProductCategories.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservice.ProductCategories.models.ProductCategoryId;
import com.example.restservice.ProductCategories.models.ProductCategoryModel;

public interface JpaProductCategoryRepository
    extends JpaRepository<ProductCategoryModel, ProductCategoryId> {
  List<ProductCategoryModel> findByProductId(UUID productId);
}
