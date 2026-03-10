package com.example.restservice.Orders.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public record DeleteOrderRequestDTO(
        @NotNull Long orderId,
        @NotNull UUID userId) {
}
