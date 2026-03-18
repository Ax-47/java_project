package com.example.restservice.Products.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restservice.Products.models.ProductModel;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductModel, UUID> {
  public Page<ProductModel> findByIdIn(List<UUID> productIds, Pageable pageable);
}
