package com.example.restservice.Products.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.dto.*;

import jakarta.transaction.Transactional;

@Service
public class FindProductUsecase {
  private final DatabaseProductRepository databaseProductRepository;

  FindProductUsecase(DatabaseProductRepository databaseProductRepository) {
    this.databaseProductRepository = databaseProductRepository;
  }

  @Transactional
  public ProductResponseDTO execute(UUID productId) {
    Product product =
        databaseProductRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    return ProductResponseDTO.from(product);
  }
}
