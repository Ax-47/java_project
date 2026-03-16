package com.example.restservice.Frontend.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCardDTO(
    UUID id, String name, String description, BigDecimal price, String thumbnailUrl) {}
