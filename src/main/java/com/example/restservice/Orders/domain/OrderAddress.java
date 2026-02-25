package com.example.restservice.Orders.domain;

import java.util.Objects;

import com.example.restservice.Address.domain.PhoneNumber;
import com.example.restservice.Orders.exceptions.AddressValidationException;

public class OrderAddress {

  private final String fullName;
  private final PhoneNumber phoneNumber;
  private final String addressLine1;
  private final String addressLine2;
  private final String subDistrict;
  private final String district;
  private final String province;
  private final String postalCode;
  private final String country;

  public OrderAddress(
      String fullName,
      PhoneNumber phoneNumber,
      String addressLine1,
      String addressLine2,
      String subDistrict,
      String district,
      String province,
      String postalCode,
      String country) {

    if (fullName == null || fullName.isBlank())
      throw new AddressValidationException("fullName", "Full name is required");

    this.fullName = fullName;
    this.phoneNumber = Objects.requireNonNull(phoneNumber);

    this.addressLine1 = require("addressLine1", addressLine1, "Address line 1 is required");
    this.addressLine2 = addressLine2;
    this.subDistrict = subDistrict;
    this.district = require("district", district, "District is required");
    this.province = require("province", province, "Province is required");
    this.postalCode = require("postalCode", postalCode, "Postal code is required");
    this.country = require("country", country, "Country is required");
  }

  private String require(String field, String value, String message) {
    if (value == null || value.isBlank())
      throw new AddressValidationException(field, message);
    return value;
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
}
