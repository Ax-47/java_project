package com.example.restservice.Frontend.usecases;

import java.util.ArrayList;
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
import com.example.restservice.ProductCategories.domain.ProductCategory;
import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

@Service
public class GetHomePageUsecase {

  private final DatabaseCategoryRepository databaseCategoryRepository;
  private final DatabaseProductRepository databaseProductRepository;
  private final DatabaseProductCategoryRepository databaseProductCategoryRepository;
  private final DatabaseImageRepository databaseImageRepository;

  public GetHomePageUsecase(
      DatabaseCategoryRepository databaseCategoryRepository,
      DatabaseProductRepository databaseProductRepository,
      DatabaseImageRepository databaseImageRepository,
      DatabaseProductCategoryRepository databaseProductCategoryRepository) {
    this.databaseCategoryRepository = databaseCategoryRepository;
    this.databaseProductRepository = databaseProductRepository;
    this.databaseImageRepository = databaseImageRepository;
    this.databaseProductCategoryRepository = databaseProductCategoryRepository;
  }

  public List<CategorySectionDTO> execute(UUID activeCategoryId, PageQuery query) {
    PageQuery categoryQuery = new PageQuery(0, 100, "categoryName", true);
    List<Category> categories =
        databaseCategoryRepository.findAllCategories(categoryQuery).content();

    List<CategorySectionDTO> result = new ArrayList<>();

    for (Category category : categories) {

      if (activeCategoryId != null && !category.getId().equals(activeCategoryId)) {
        result.add(new CategorySectionDTO(category.getId(), category.getCategoryName(), List.of()));
        continue;
      }

      Page<ProductCategory> productCategoryPage =
          databaseProductCategoryRepository.findByCategoryIds(List.of(category.getId()), query);
      List<UUID> productIds =
          productCategoryPage.content().stream().map(ProductCategory::getProductId).toList();
      List<ProductCardDTO> productDTOs = new ArrayList<>();
      if (!productIds.isEmpty()) {
        PageQuery pQuery = new PageQuery(0, productIds.size(), "productName", true);
        Map<UUID, Product> productMap =
            databaseProductRepository.findByIds(productIds, pQuery).content().stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        Map<UUID, List<Image>> imagesByProductId =
            databaseImageRepository.findProductImages(productIds).stream()
                .collect(Collectors.groupingBy(img -> img.getResource().getResourceId()));

        for (UUID pid : productIds) {
          Product p = productMap.get(pid);
          if (p != null) {
            productDTOs.add(mapToProductCardDTO(p, imagesByProductId.getOrDefault(pid, List.of())));
          }
        }
      }

      result.add(new CategorySectionDTO(category.getId(), category.getCategoryName(), productDTOs));
    }

    return result;
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
