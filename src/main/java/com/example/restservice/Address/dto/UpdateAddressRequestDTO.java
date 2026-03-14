package com.example.restservice.Address.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record UpdateAddressRequestDTO(
    @NotNull(message = "Address ID is required for update") UUID addressId,
    String fullName,
    String phoneNumber,
    String addressLine1,
    String addressLine2,
    String subDistrict,
    String district,
    String province,
    String postalCode,
    String country,
    String label,
    Boolean isDefault) {}
