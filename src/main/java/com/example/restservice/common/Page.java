package com.example.restservice.common;

import java.util.List;

public record Page<T>(List<T> content, long totalElements, int totalPages, int page, int size) {
}
