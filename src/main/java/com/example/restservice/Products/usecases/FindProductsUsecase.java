package com.example.restservice.Products.usecases;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;
import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.dto.ProductResponseDTO;
import com.example.restservice.common.*;

@Service
public class FindProductsUsecase {

  private final DatabaseProductRepository databaseProductRepository;
  private final DatabaseImageRepository databaseImageRepository;

  public FindProductsUsecase(
      DatabaseProductRepository databaseProductRepository,
      DatabaseImageRepository databaseImageRepository) {
    this.databaseProductRepository = databaseProductRepository;
    this.databaseImageRepository = databaseImageRepository;
  }

  public Page<ProductResponseDTO> execute(PageQuery query) {
    Page<Product> productsPage = databaseProductRepository.findAllProducts(query);
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
