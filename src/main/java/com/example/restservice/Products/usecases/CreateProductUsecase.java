package com.example.restservice.Products.usecases;

import com.example.restservice.Products.domain.*;
import com.example.restservice.Products.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductUsecase {

    private final DatabaseProductRepository productRepository;

    public CreateProductUsecase(DatabaseProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public CreateProductResponseDTO execute(CreateProductRequestDTO request) {
        Product newProduct = Product.create(
            request.name(),
            request.price(),
            request.description(),
            request.createdBy()
        );

        productRepository.save(newProduct);

        return new CreateProductResponseDTO("Product was created");
    }
}
