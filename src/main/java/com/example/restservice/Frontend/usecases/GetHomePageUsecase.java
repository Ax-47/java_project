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

    PageQuery categoryQuery = new PageQuery(0, 50, "categoryName", true);
    Page<Category> categoriesPage = databaseCategoryRepository.findAllCategories(categoryQuery);
    List<Category> categories = categoriesPage.content();

    List<UUID> targetCategoryIds;
    if (activeCategoryId != null) {
      targetCategoryIds = List.of(activeCategoryId);
    } else {
      targetCategoryIds = categories.stream().map(Category::getId).toList();
    }

    PageQuery productCategoryQuery = new PageQuery(0, 50, "Id", true);
    Page<ProductCategory> productCategoryPage =
        databaseProductCategoryRepository.findByCategoryIds(
            targetCategoryIds, productCategoryQuery);
    List<ProductCategory> productCategory = productCategoryPage.content();

    List<UUID> productIds =
        productCategory.stream().map(ProductCategory::getProductId).distinct().toList();

    List<Image> images = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    PageQuery productQuery = new PageQuery(0, 50, "productName", true);

    if (!productIds.isEmpty()) {
      products = databaseProductRepository.findByIds(productIds, productQuery).content();
      images = databaseImageRepository.findProductImages(productIds);
    }

    Map<UUID, Product> productMap =
        products.stream().collect(Collectors.toMap(Product::getId, p -> p));

    Map<UUID, List<Image>> imagesByProductId =
        images.stream()
            .collect(Collectors.groupingBy(image -> image.getResource().getResourceId()));

    Map<UUID, List<Product>> productsByCategoryId =
        productCategory.stream()
            .filter(pc -> productMap.containsKey(pc.getProductId()))
            .collect(
                Collectors.groupingBy(
                    ProductCategory::getCategoryId,
                    Collectors.mapping(
                        pc -> productMap.get(pc.getProductId()), Collectors.toList())));

    List<CategorySectionDTO> result = new ArrayList<>();

    for (Category category : categories) {
      if (activeCategoryId == null || category.getId().equals(activeCategoryId)) {
        List<Product> catProducts = productsByCategoryId.getOrDefault(category.getId(), List.of());
        List<ProductCardDTO> productDTOs =
            catProducts.stream()
                .map(
                    p -> {
                      List<Image> pImages = imagesByProductId.getOrDefault(p.getId(), List.of());

                      String thumbnail =
                          !pImages.isEmpty()
                              ? "/images"
                                  + pImages
                                      .getFirst()
                                      .getResource()
                                      .genFilename(pImages.getFirst().getId(), ImageSize.THUMBNAIL)
                              : "/images/product-mac.png";

                      return new ProductCardDTO(
                          p.getId(),
                          p.getName(),
                          p.getDescription(),
                          p.getPrice().getValue(),
                          thumbnail);
                    })
                .toList();

        result.add(
            new CategorySectionDTO(category.getId(), category.getCategoryName(), productDTOs));

      } else {
        result.add(new CategorySectionDTO(category.getId(), category.getCategoryName(), List.of()));
      }
    }

    return result;
  }
}
