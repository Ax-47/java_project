package com.example.restservice.Products.usecases;

import com.example.restservice.Products.domain.*;
import org.springframework.stereotype.Service;
import com.example.restservice.Products.dto.DeleteProductRequestDTO;
import com.example.restservice.Products.dto.DeleteProductResponseDTO;

@Service
public class DeleteProductUsecase {

    private final DatabaseProductRepository databaseProductRepository;

    public DeleteProductUsecase(
            DatabaseProductRepository databaseProductRepository) {
        this.databaseProductRepository = databaseProductRepository;
    }

    public DeleteProductResponseDTO execute(DeleteProductRequestDTO request) {
        Product existingProduct = this.databaseProductRepository.findById(
                request.productId()).orElseThrow(() -> new RuntimeException("Product not found"));

        if (!existingProduct.getCreatedBy().equals(request.userId())) {
            throw new RuntimeException("Unauthorized to delete this product");
        }
        this.databaseProductRepository.delete(existingProduct);

        return new DeleteProductResponseDTO("Product was deleted successfully");
    }
}
