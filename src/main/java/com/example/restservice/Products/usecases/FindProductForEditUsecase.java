package com.example.restservice.Products.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.usecases.FindImageUsecase;
import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;
import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.dto.ProductEditViewResponseDTO;

@Service
public class FindProductForEditUsecase {

  private final DatabaseProductRepository databaseProductRepository;
  private final DatabaseCategoryRepository databaseCategoryRepository;
  private final DatabaseProductCategoryRepository databaseProductCategoryRepository;
  private final FindImageUsecase findImageUsecase;

  public FindProductForEditUsecase(
      DatabaseProductRepository databaseProductRepository,
      DatabaseCategoryRepository databaseCategoryRepository,
      DatabaseProductCategoryRepository databaseProductCategoryRepository,
      FindImageUsecase findImageUsecase) {
    this.databaseProductRepository = databaseProductRepository;
    this.databaseCategoryRepository = databaseCategoryRepository;
    this.databaseProductCategoryRepository = databaseProductCategoryRepository;
    this.findImageUsecase = findImageUsecase;
  }

  public ProductEditViewResponseDTO execute(UUID productId) {
    var product = databaseProductRepository.findById(productId).orElseThrow();
    var allCategories = databaseCategoryRepository.findAll();
    var productCategories = databaseProductCategoryRepository.findCategoriesByProductId(productId);
    var images = findImageUsecase.execute(productId, ImageResourceType.PRODUCT);

    return new ProductEditViewResponseDTO(product, allCategories, productCategories, images);
  }
}
