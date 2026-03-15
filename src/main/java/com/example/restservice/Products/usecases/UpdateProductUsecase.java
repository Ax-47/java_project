package com.example.restservice.Products.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.domain.ImageResource;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.dto.ProductResponseDTO;
import com.example.restservice.Products.dto.UpdateProductRequestDTO;

@Service
public class UpdateProductUsecase {

  private final DatabaseProductRepository databaseProductRepository;
  private final DatabaseImageRepository databaseImageRepository;

  public UpdateProductUsecase(
      DatabaseProductRepository databaseProductRepository,
      DatabaseImageRepository databaseImageRepository) {
    this.databaseProductRepository = databaseProductRepository;
    this.databaseImageRepository = databaseImageRepository;
  }

  public ProductResponseDTO execute(UUID id, UpdateProductRequestDTO request) {

    Product product =
        databaseProductRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    product.update(request.name(), request.price(), request.description());

    List<Image> images =
        databaseImageRepository.findByResource(ImageResource.of(id, ImageResourceType.PRODUCT));
    databaseProductRepository.save(product);
    return ProductResponseDTO.from(product, images);
  }
}
