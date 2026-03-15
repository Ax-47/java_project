package com.example.restservice.Orders.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequestDTO(
    @NotNull UUID productId,
    @NotNull UUID userId) {}
