package com.example.restservice.Categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
    @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Category name must be less than 100 characters")
        String name) {}
