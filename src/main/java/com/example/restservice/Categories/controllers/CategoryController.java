package com.example.restservice.Categories.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Categories.dto.*;
import com.example.restservice.Categories.usecases.*;
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

  public CategoryController(
      CreateCategoryUsecase createCategoryUsecase,
      UpdateCategoryUsecase updateCategoryUsecase,
      GetCategoryUsecase getCategoryUsecase,
      FindCategoriesUsecase findCategoriesUsecase,
      DeleteCategoryUsecase deleteCategoryUsecase) {
    this.createCategoryUsecase = createCategoryUsecase;
    this.updateCategoryUsecase = updateCategoryUsecase;
    this.deleteCategoryUsecase = deleteCategoryUsecase;
    this.getCategoryUsecase = getCategoryUsecase;
    this.findCategoriesUsecase = findCategoriesUsecase;
  }

  @PostMapping
  public ResponseEntity<CategoryResponseDTO> create(
      @Valid @RequestBody CategoryRequestDTO request) {
    return ResponseEntity.ok(createCategoryUsecase.execute(request));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CategoryResponseDTO> rename(
      @PathVariable UUID id, @Valid @RequestBody CategoryRequestDTO request) {

    return ResponseEntity.ok(updateCategoryUsecase.execute(id, request));
  }

  @GetMapping
  public ResponseEntity<PageResponse<CategoryResponseDTO>> findAllUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "categoryName") String sortBy,
      @RequestParam(defaultValue = "true") boolean asc) {

    PageQuery query = new PageQuery(page, size, sortBy, asc);
    return ResponseEntity.ok(PageResponse.from(findCategoriesUsecase.execute(query)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponseDTO> findById(@PathVariable UUID id) {

    return ResponseEntity.ok(getCategoryUsecase.execute(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {

    deleteCategoryUsecase.execute(id);
    return ResponseEntity.noContent().build();
  }
  // GET /api/categories/{categoryId}/products
}
