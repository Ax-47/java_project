package com.example.restservice.Frontend.dto;

import java.util.List;
import java.util.UUID;

public record CategorySectionDTO(UUID id, String name, List<ProductCardDTO> products) {}
