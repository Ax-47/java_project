package com.example.restservice.Address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAddressRequestDTO(
    @NotNull(message = "User ID is required")
    Long userId,

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    String fullName,

    @NotBlank(message = "Phone number is required")
    @Size(max = 10, message = "Phone number must not exceed 10 characters")
    String phoneNumber,

    @NotBlank(message = "Address line 1 is required")
    @Size(max = 100, message = "Address line 1 must not exceed 100 characters")
    String addressLine1,

    @Size(max = 100, message = "Address line 2 must not exceed 100 characters")
    String addressLine2,

    @NotBlank(message = "Sub-district is required")
    @Size(max = 100, message = "Sub-district must not exceed 100 characters")
    String subDistrict,

    @NotBlank(message = "District is required")
    @Size(max = 100, message = "District must not exceed 100 characters")
    String district,

    @NotBlank(message = "Province is required")
    @Size(max = 100, message = "Province must not exceed 100 characters")
    String province,

    @NotBlank(message = "Postal code is required")
    @Size(max = 10, message = "Postal code must not exceed 10 characters")
    String postalCode,

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    String country,

    @Size(max = 100, message = "Label must not exceed 100 characters")
    String label,

    Boolean isDefault
) {
}
