package com.example.restservice.Products.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;
import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.dto.DeleteProductRequestDTO;
import com.example.restservice.Products.dto.DeleteProductResponseDTO;
import com.example.restservice.Products.exceptions.ProductNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class DeleteProductUsecase {

  private final DatabaseProductRepository databaseProductRepository;
  private final DatabaseProductCategoryRepository databaseProductCategoryRepository;

  public DeleteProductUsecase(
      DatabaseProductCategoryRepository databaseProductCategoryRepository,
      DatabaseProductRepository databaseProductRepository) {
    this.databaseProductCategoryRepository = databaseProductCategoryRepository;
    this.databaseProductRepository = databaseProductRepository;
  }

  @Transactional
  public DeleteProductResponseDTO execute(DeleteProductRequestDTO request) {
    Product existingProduct =
        this.databaseProductRepository
            .findById(request.productId())
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

    databaseProductCategoryRepository.deleteByProductId(request.productId());
    this.databaseProductRepository.delete(existingProduct);

    return new DeleteProductResponseDTO("Product was deleted successfully");
  }
}
