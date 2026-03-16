package com.example.restservice.Frontend.usecases;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Frontend.dto.CategorySectionDTO;
import com.example.restservice.Frontend.dto.ProductCardDTO;
import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.domain.ImageSize;
import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

@Service
public class GetCategoryPageUsecase {

  private final DatabaseCategoryRepository databaseCategoryRepository;
  private final DatabaseProductCategoryRepository databaseProductCategoryRepository;
  private final DatabaseImageRepository databaseImageRepository;

  public GetCategoryPageUsecase(
      DatabaseCategoryRepository databaseCategoryRepository,
      DatabaseImageRepository databaseImageRepository,
      DatabaseProductCategoryRepository databaseProductCategoryRepository) {
    this.databaseCategoryRepository = databaseCategoryRepository;
    this.databaseImageRepository = databaseImageRepository;
    this.databaseProductCategoryRepository = databaseProductCategoryRepository;
  }

  public List<CategorySectionDTO> execute(UUID activeCategoryId, PageQuery query) {
    Category category =
        databaseCategoryRepository
            .findById(activeCategoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    Page<Product> productPage =
        databaseProductCategoryRepository.findProductsByCategoryId(category.getId(), query);

    List<Product> products = productPage.content();
    List<UUID> productIds = products.stream().map(Product::getId).toList();

    Map<UUID, List<Image>> imagesByProductId = Map.of();
    if (!productIds.isEmpty()) {
      imagesByProductId =
          databaseImageRepository.findProductImages(productIds).stream()
              .collect(Collectors.groupingBy(image -> image.getResource().getResourceId()));
    }

    final Map<UUID, List<Image>> finalImagesMap = imagesByProductId;
    List<ProductCardDTO> productDTOs =
        products.stream()
            .map(p -> mapToProductCardDTO(p, finalImagesMap.getOrDefault(p.getId(), List.of())))
            .toList();

    return List.of(
        new CategorySectionDTO(category.getId(), category.getCategoryName(), productDTOs));
  }

  private ProductCardDTO mapToProductCardDTO(Product p, List<Image> pImages) {
    String thumbnail = "/images/product-mac.png";
    if (!pImages.isEmpty()) {
      Image firstImg = pImages.getFirst();
      thumbnail =
          "/images" + firstImg.getResource().genFilename(firstImg.getId(), ImageSize.THUMBNAIL);
    }

    return new ProductCardDTO(
        p.getId(), p.getName(), p.getDescription(), p.getPrice().getValue(), thumbnail);
  }
}
