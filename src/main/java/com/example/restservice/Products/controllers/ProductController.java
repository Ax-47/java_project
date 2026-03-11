package com.example.restservice.Products.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.Products.dto.CreateProductRequestDTO;
import com.example.restservice.Products.dto.CreateProductResponseDTO;
import com.example.restservice.Products.dto.DeleteProductRequestDTO;
import com.example.restservice.Products.dto.DeleteProductResponseDTO;
import com.example.restservice.Products.usecases.CreateProductUsecase;
import com.example.restservice.Products.usecases.DeleteProductUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/products")
public class ProductController {
  private CreateProductUsecase createProductUsecase;
  private DeleteProductUsecase deleteProductUsecase;

  public ProductController(CreateProductUsecase createProductUsecase, DeleteProductUsecase deleteProductUsecase) {
    this.createProductUsecase = createProductUsecase;
    this.deleteProductUsecase = deleteProductUsecase;
  }

  @PostMapping
  public ResponseEntity<CreateProductResponseDTO> create(@Valid @RequestBody CreateProductRequestDTO requestModel) {
    CreateProductResponseDTO response = createProductUsecase.execute(requestModel);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping
  public ResponseEntity<DeleteProductResponseDTO> delete(@Valid @RequestBody DeleteProductRequestDTO requestModel){
    DeleteProductResponseDTO response = deleteProductUsecase.execute(requestModel);
    return ResponseEntity.ok(response);
  }
}
