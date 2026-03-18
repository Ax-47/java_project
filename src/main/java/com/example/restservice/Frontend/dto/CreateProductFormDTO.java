package com.example.restservice.Frontend.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.restservice.Products.dto.CreateProductRequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateProductFormDTO {
  @NotBlank(message = "Product name is required")
  @Size(max = 255, message = "Product name too long")
  String name;

  @Size(max = 511, message = "Description too long")
  String description;

  @NotNull(message = "Price is required")
  @Positive(message = "Price must be greater than zero")
  BigDecimal price;

  public CreateProductFormDTO() {}

  public CreateProductRequestDTO toRequest(UUID createBy) {
    return new CreateProductRequestDTO(this.name, this.description, this.price, createBy);
  }

  public CreateProductFormDTO setName(String name) {
    this.name = name;
    return this;
  }

  public CreateProductFormDTO setDescription(String description) {
    this.description = description;
    return this;
  }

  public CreateProductFormDTO setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }
}
