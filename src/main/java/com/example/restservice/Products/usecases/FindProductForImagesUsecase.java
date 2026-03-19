package com.example.restservice.Products.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.usecases.FindImageUsecase;
import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.dto.ProductImagesViewResponseDTO;

@Service
public class FindProductForImagesUsecase {

  private final DatabaseProductRepository databaseProductRepository;
  private final FindImageUsecase findImageUsecase;

  public FindProductForImagesUsecase(
      DatabaseProductRepository databaseProductRepository, FindImageUsecase findImageUsecase) {
    this.databaseProductRepository = databaseProductRepository;
    this.findImageUsecase = findImageUsecase;
  }

  public ProductImagesViewResponseDTO execute(UUID productId) {
    var product = databaseProductRepository.findById(productId).orElseThrow();

    var images = findImageUsecase.execute(productId, ImageResourceType.PRODUCT);

    return new ProductImagesViewResponseDTO(product, images);
  }
}
