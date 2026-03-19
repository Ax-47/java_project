package com.example.restservice.Products.dto;

import java.util.List;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Images.dto.UploadImageResponseDTO;
import com.example.restservice.Products.domain.Product;

public record ProductEditViewResponseDTO(
    Product product,
    List<Category> allCategories,
    List<Category> productCategories,
    List<UploadImageResponseDTO> images) {}
