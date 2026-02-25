package com.example.restservice.Users.domain;

public record PageQuery(
    int page,
    int size,
    String sortBy,
    boolean ascending) {
}
