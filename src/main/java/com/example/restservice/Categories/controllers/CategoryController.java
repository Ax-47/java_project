package com.example.restservice.Categories.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Categories.dto.CategoryRequestDTO;
import com.example.restservice.Categories.dto.CategoryResponseDTO;
import com.example.restservice.Categories.usecases.CreateCategoryUsecase;
import com.example.restservice.Categories.usecases.DeleteCategoryUsecase;
import com.example.restservice.Categories.usecases.UpdateCategoryUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CreateCategoryUsecase createCategoryUsecase;
  private final UpdateCategoryUsecase updateCategoryUsecase;
  private final DeleteCategoryUsecase deleteCategoryUsecase;

  public CategoryController(
      CreateCategoryUsecase createCategoryUsecase,
      UpdateCategoryUsecase updateCategoryUsecase,
      DeleteCategoryUsecase deleteCategoryUsecase) {
    this.createCategoryUsecase = createCategoryUsecase;
    this.updateCategoryUsecase = updateCategoryUsecase;
    this.deleteCategoryUsecase = deleteCategoryUsecase;
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

  // @GetMapping
  // public ResponseEntity<List<CategoryResponseDTO>> findAll() {
  // return ResponseEntity.ok(categoryService.findAll());
  // }
  //
  // @GetMapping("/{id}")
  // public ResponseEntity<CategoryResponseDTO> findById(
  // @PathVariable UUID id) {
  //
  // return ResponseEntity.ok(categoryService.findById(id));
  // }
  //
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {

    deleteCategoryUsecase.execute(id);
    return ResponseEntity.noContent().build();
  }
}
