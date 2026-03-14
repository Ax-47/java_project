package com.example.restservice.Products.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Products.domain.*;
import com.example.restservice.Products.dto.*;

@Service
public class CreateProductUsecase {

  private final DatabaseProductRepository productRepository;

  public CreateProductUsecase(DatabaseProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional
  public CreateProductResponseDTO execute(CreateProductRequestDTO request) {
    Product newProduct =
        Product.create(
            request.name(),
            request.price(),
            request.description(),
            request.createdBy(),
            request.categoryId());

    productRepository.save(newProduct);

    return new CreateProductResponseDTO("Product was created");
  }
}
