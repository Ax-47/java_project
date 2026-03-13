package com.example.restservice.common;

public record PageQuery(int page, int size, String sortBy, boolean ascending) {
}
