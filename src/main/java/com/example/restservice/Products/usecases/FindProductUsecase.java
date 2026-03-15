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
import com.example.restservice.Products.dto.*;

import jakarta.transaction.Transactional;

@Service
public class FindProductUsecase {
  private final DatabaseProductRepository databaseProductRepository;

  private final DatabaseImageRepository databaseImageRepository;

  FindProductUsecase(
      DatabaseProductRepository databaseProductRepository,
      DatabaseImageRepository databaseImageRepository) {
    this.databaseProductRepository = databaseProductRepository;
    this.databaseImageRepository = databaseImageRepository;
  }

  @Transactional
  public ProductResponseDTO execute(UUID productId) {
    Product product =
        databaseProductRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    List<Image> images =
        databaseImageRepository.findByResource(
            ImageResource.of(productId, ImageResourceType.PRODUCT));
    return ProductResponseDTO.from(product, images);
  }
}
