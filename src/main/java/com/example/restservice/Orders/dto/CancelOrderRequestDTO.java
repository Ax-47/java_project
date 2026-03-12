package com.example.restservice.Orders.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CancelOrderRequestDTO(@NotNull UUID orderId, @NotNull UUID userId) {}
