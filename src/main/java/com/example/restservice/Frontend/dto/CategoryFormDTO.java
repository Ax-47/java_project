package com.example.restservice.Frontend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryFormDTO {
  @NotBlank(message = "Category name is required")
  @Size(max = 100, message = "Category name must be less than 100 characters")
  String name;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
