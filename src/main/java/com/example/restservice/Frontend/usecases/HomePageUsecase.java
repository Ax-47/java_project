// package com.example.restservice.Frontend.usecases;
//
// import java.util.List;
// import java.util.stream.Collectors;
//
// import com.example.restservice.Categories.domain.Category;
// import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
// import com.example.restservice.Frontend.dto.CategoryWithProducts;
// import com.example.restservice.Products.domain.DatabaseProductRepository;
// import com.example.restservice.Products.dto.ProductResponseDTO;
//
// @Service
// public class FindHomePageUsecase {
//
// private final DatabaseCategoryRepository databaseCategoryRepository;
// private final DatabaseProductRepository databaseProductRepository;
//
// public FindHomePageUsecase(
// DatabaseCategoryRepository databaseCategoryRepository,
// DatabaseProductRepository databaseProductRepository) {
// this.databaseCategoryRepository = databaseCategoryRepository;
// this.databaseProductRepository = databaseProductRepository;
// }
//
// @Override
// public List<CategoryWithProducts> execute() {
//
// var categories = databaseCategoryRepository.findAll();
//
// var categoryIds = categories.stream()
// .map(Category::getId)
// .toList();
//
// var products =
// databaseProductRepository.findTopProductsByCategoryIds(categoryIds, 50);
//
// Map<UUID, List<ProductResponseDTO>> productsByCategory = products.stream()
// .collect(Collectors.groupingBy(ProductResponseDTO::categoryId));
//
// return categories.stream()
// .map(category -> new CategoryWithProducts(
// category.getId(),
// category.getName(),
// productsByCategory.getOrDefault(category.getId(), List.of())))
// .toList();
// }
// }
