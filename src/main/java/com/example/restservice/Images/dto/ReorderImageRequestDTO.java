package com.example.restservice.Images.dto;

import java.util.UUID;

public record ReorderImageRequestDTO(UUID imageId, int sortOrder) {}
