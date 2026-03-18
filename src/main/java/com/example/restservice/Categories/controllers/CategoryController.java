package com.example.restservice.Categories.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Categories.domain.CategorySortField;
import com.example.restservice.Categories.dto.*;
import com.example.restservice.Categories.usecases.*;
import com.example.restservice.ProductCategories.usecases.FindProductsByCategoryIdUsecase;
import com.example.restservice.Products.domain.ProductSortField;
import com.example.restservice.Products.dto.ProductResponseDTO;
import com.example.restservice.common.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CreateCategoryUsecase createCategoryUsecase;
  private final UpdateCategoryUsecase updateCategoryUsecase;
  private final DeleteCategoryUsecase deleteCategoryUsecase;
  private final GetCategoryUsecase getCategoryUsecase;
  private final FindCategoriesUsecase findCategoriesUsecase;
  private final FindProductsByCategoryIdUsecase findProductsByCategoryIdUsecase;

  public CategoryController(
      CreateCategoryUsecase createCategoryUsecase,
      UpdateCategoryUsecase updateCategoryUsecase,
      GetCategoryUsecase getCategoryUsecase,
      FindCategoriesUsecase findCategoriesUsecase,
      FindProductsByCategoryIdUsecase findProductsByCategoryIdUsecase,
      DeleteCategoryUsecase deleteCategoryUsecase) {
    this.createCategoryUsecase = createCategoryUsecase;
    this.updateCategoryUsecase = updateCategoryUsecase;
    this.deleteCategoryUsecase = deleteCategoryUsecase;
    this.getCategoryUsecase = getCategoryUsecase;
    this.findProductsByCategoryIdUsecase = findProductsByCategoryIdUsecase;
    this.findCategoriesUsecase = findCategoriesUsecase;
  }

  // post /api/categories
  @PostMapping
  public ResponseEntity<CategoryResponseDTO> create(
      @Valid @RequestBody CategoryRequestDTO request) {
    return ResponseEntity.ok(createCategoryUsecase.execute(request));
  }

  // patch /api/categories/{id}
  @PatchMapping("/{id}")
  public ResponseEntity<CategoryResponseDTO> rename(
      @PathVariable UUID id, @Valid @RequestBody CategoryRequestDTO request) {

    return ResponseEntity.ok(updateCategoryUsecase.execute(id, request));
  }

  // get /api/categories
  @GetMapping
  public ResponseEntity<PageResponse<CategoryResponseDTO>> findAllUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "categoryName") CategorySortField sortBy,
      @RequestParam(defaultValue = "true") boolean asc) {
    PageQuery query = new PageQuery(page, size, sortBy.name(), asc);
    return ResponseEntity.ok(PageResponse.from(findCategoriesUsecase.execute(query)));
  }

  // get /api/categories//{id}
  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponseDTO> findById(@PathVariable UUID id) {

    return ResponseEntity.ok(getCategoryUsecase.execute(id));
  }

  // del /api/categories//{id}
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {

    deleteCategoryUsecase.execute(id);
    return ResponseEntity.noContent().build();
  }

  // GET /api/categories/{categoryId}/products
  @GetMapping("/{categoryId}/products")
  public ResponseEntity<PageResponse<ProductResponseDTO>> findProductsByCategoryId(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "productName") ProductSortField sortBy,
      @RequestParam(defaultValue = "true") boolean asc,
      @PathVariable UUID categoryId) {
    PageQuery query = new PageQuery(page, size, sortBy.name(), asc);
    return ResponseEntity.ok(
        PageResponse.from(findProductsByCategoryIdUsecase.execute(categoryId, query)));
  }
}
