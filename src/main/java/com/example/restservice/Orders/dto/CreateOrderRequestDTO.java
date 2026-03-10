package com.example.restservice.Orders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateOrderRequestDTO(
    @NotNull UUID productId,
    @NotBlank String fullName,
    @NotBlank String phoneNumber,
    @NotBlank String addressLine1,
    String addressLine2,
    String subDistrict,
    @NotBlank String district,
    @NotBlank String province,
    @NotBlank String postalCode,
    @NotBlank String country
) {}
