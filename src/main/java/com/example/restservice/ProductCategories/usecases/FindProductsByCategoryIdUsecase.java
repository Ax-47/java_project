package com.example.restservice.ProductCategories.usecases;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;
import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.dto.ProductResponseDTO;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

@Service
public class FindProductsByCategoryIdUsecase {

  private final DatabaseProductCategoryRepository databaseProductCategoryRepository;
  private final DatabaseImageRepository databaseImageRepository;

  public FindProductsByCategoryIdUsecase(
      DatabaseImageRepository databaseImageRepository,
      DatabaseProductCategoryRepository databaseProductCategoryRepository) {
    this.databaseImageRepository = databaseImageRepository;
    this.databaseProductCategoryRepository = databaseProductCategoryRepository;
  }

  public Page<ProductResponseDTO> execute(UUID categoryId, PageQuery query) {
    Page<Product> productsPage =
        databaseProductCategoryRepository.findProductsByCategoryId(categoryId, query);
    List<Product> products = productsPage.content();
    List<UUID> productIds = products.stream().map(Product::getId).toList();
    List<Image> images = databaseImageRepository.findProductImages(productIds);
    Map<UUID, List<Image>> imageMap =
        images.stream().collect(Collectors.groupingBy(img -> img.getResource().getResourceId()));
    List<ProductResponseDTO> content =
        products.stream()
            .map(
                product -> {
                  List<Image> productImages = imageMap.getOrDefault(product.getId(), List.of());

                  return ProductResponseDTO.from(product, productImages);
                })
            .toList();
    return new Page<>(
        content,
        productsPage.totalElements(),
        productsPage.totalPages(),
        productsPage.page(),
        productsPage.size());
  }
}
