package com.example.restservice.Products.dto;

import java.math.BigDecimal;

public record UpdateProductRequestDTO(String name, BigDecimal price, String description) {}
