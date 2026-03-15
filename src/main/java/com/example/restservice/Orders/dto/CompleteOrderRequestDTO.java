package com.example.restservice.Orders.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CompleteOrderRequestDTO(@NotNull UUID orderId, @NotNull UUID userId) {}
