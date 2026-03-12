package com.example.restservice.Products.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.dto.DeleteProductRequestDTO;
import com.example.restservice.Products.dto.DeleteProductResponseDTO;
import com.example.restservice.Products.exceptions.ProductNotFoundException;
import com.example.restservice.Products.exceptions.UnauthorizedProductActionException;

@Service
public class DeleteProductUsecase {

  private final DatabaseProductRepository databaseProductRepository;

  public DeleteProductUsecase(DatabaseProductRepository databaseProductRepository) {
    this.databaseProductRepository = databaseProductRepository;
  }

  public DeleteProductResponseDTO execute(DeleteProductRequestDTO request) {
    Product existingProduct =
        this.databaseProductRepository
            .findById(request.productId())
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

    if (!existingProduct.getCreatedBy().equals(request.userId())) {
      throw new UnauthorizedProductActionException("Unauthorized to delete this product");
    }
    this.databaseProductRepository.delete(existingProduct);

    return new DeleteProductResponseDTO("Product was deleted successfully");
  }
}
