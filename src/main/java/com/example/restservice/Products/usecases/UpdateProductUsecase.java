package com.example.restservice.Products.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.dto.ProductResponseDTO;
import com.example.restservice.Products.dto.UpdateProductRequestDTO;

@Service
public class UpdateProductUsecase {

  private final DatabaseProductRepository databaseProductRepository;

  public UpdateProductUsecase(DatabaseProductRepository databaseProductRepository) {
    this.databaseProductRepository = databaseProductRepository;
  }

  public ProductResponseDTO execute(UUID id, UpdateProductRequestDTO request) {

    Product product =
        databaseProductRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    product.update(request.name(), request.price(), request.description());

    databaseProductRepository.save(product);
    return ProductResponseDTO.from(product);
  }
}
