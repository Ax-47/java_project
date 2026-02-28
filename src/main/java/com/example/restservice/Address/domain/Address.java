package com.example.restservice.Address.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Address {
    private final UUID id;
    private final UUID userId;
    private String fullName;
    private PhoneNumber phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String subDistrict;
    private String district;
    private String province;
    private String postalCode;
    private String country;
    private String label;
    private boolean isDefault;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Address(
            UUID id,
            UUID userId,
            String fullName,
            PhoneNumber phoneNumber,
            String addressLine1,
            String addressLine2,
            String subDistrict,
            String district,
            String province,
            String postalCode,
            String country,
            String label,
            boolean isDefault,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        validate(fullName, addressLine1, province, postalCode, country);

        this.id = id;
        this.userId = Objects.requireNonNull(userId);
        this.fullName = fullName;
        this.phoneNumber = Objects.requireNonNull(phoneNumber);
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.subDistrict = subDistrict;
        this.district = district;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.label = label;
        this.isDefault = isDefault;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static Address create(
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
        return new Address(
                null,
                userId,
                fullName,
                PhoneNumber.of(phoneNumber),
                addressLine1,
                addressLine2,
                subDistrict,
                district,
                province,
                postalCode,
                country,
                label,
                isDefault,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public static Address rehydrate(
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
            boolean isDefault,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new Address(
                id,
                userId,
                fullName,
                PhoneNumber.of(phoneNumber),
                addressLine1,
                addressLine2,
                subDistrict,
                district,
                province,
                postalCode,
                country,
                label,
                isDefault,
                createdAt,
                updatedAt);
    }

    public void update(
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
        validate(fullName, addressLine1, province, postalCode, country);

        this.fullName = fullName;
        this.phoneNumber = PhoneNumber.of(phoneNumber);
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.subDistrict = subDistrict;
        this.district = district;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.label = label;
        this.isDefault = isDefault;
        this.updatedAt = LocalDateTime.now();
    }

    private void validate(
            String fullName,
            String addressLine1,
            String province,
            String postalCode,
            String country) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name required");
        }
        if (addressLine1 == null || addressLine1.isBlank()) {
            throw new IllegalArgumentException("Address line1 required");
        }
        if (province == null || province.isBlank()) {
            throw new IllegalArgumentException("Province required");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("Postal code required");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country required");
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public String getDistrict() {
        return district;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getLabel() {
        return label;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
