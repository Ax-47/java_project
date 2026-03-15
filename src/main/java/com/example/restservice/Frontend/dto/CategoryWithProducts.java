package com.example.restservice.Frontend.dto;

import java.util.List;
import java.util.UUID;

import com.example.restservice.Products.dto.ProductResponseDTO;

public record CategoryWithProducts(UUID id, String name, List<ProductResponseDTO> products) {}
