package com.example.restservice.Categories.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Categories.dto.CategoryRequestDTO;
import com.example.restservice.Categories.dto.CategoryResponseDTO;
import com.example.restservice.Categories.usecases.CreateCategoryUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CreateCategoryUsecase createCategoryUsecase;

  public CategoryController(CreateCategoryUsecase createCategoryUsecase) {
    this.createCategoryUsecase = createCategoryUsecase;
  }

  @PostMapping
  public ResponseEntity<CategoryResponseDTO> create(
      @Valid @RequestBody CategoryRequestDTO request) {
    return ResponseEntity.ok(createCategoryUsecase.execute(request));
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
  // @DeleteMapping("/{id}")
  // public ResponseEntity<Void> delete(
  // @PathVariable UUID id) {
  //
  // categoryService.delete(id);
  // return ResponseEntity.noContent().build();
  // }
}
