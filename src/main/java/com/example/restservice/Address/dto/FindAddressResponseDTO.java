package com.example.restservice.Address.dto;

import java.util.UUID;

import com.example.restservice.Address.domain.Address;

public record FindAddressResponseDTO(
    UUID id,
    UUID userId,
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
    boolean isDefault) {
  public static FindAddressResponseDTO from(Address address) {
    return new FindAddressResponseDTO(
        address.getId(),
        address.getUserId(),
        address.getFullName(),
        address.getPhoneNumber().value(),
        address.getAddressLine1(),
        address.getAddressLine2(),
        address.getSubDistrict(),
        address.getDistrict(),
        address.getProvince(),
        address.getPostalCode(),
        address.getCountry(),
        address.getLabel(),
        address.isDefault());
  }
}
