package com.example.restservice.Frontend.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressWebFormDTO {
  private UUID id;

  @NotBlank(message = "กรุณากรอกชื่อ-นามสกุล")
  @Size(min = 2, max = 100, message = "ชื่อ-นามสกุลต้องมีความยาว 2-100 ตัวอักษร")
  private String fullName;

  @NotBlank(message = "กรุณากรอกเบอร์โทรศัพท์")
  @Size(max = 10, message = "เบอร์โทรศัพท์ต้องไม่เกิน 10 หลัก")
  @Pattern(regexp = "^0[0-9]{8,9}$", message = "รูปแบบเบอร์โทรศัพท์ไม่ถูกต้อง (เช่น 0812345678)")
  private String phoneNumber;

  @NotBlank(message = "กรุณากรอกที่อยู่")
  private String addressLine1;

  private String addressLine2;

  @NotBlank(message = "กรุณากรอกแขวง/ตำบล")
  private String subDistrict;

  @NotBlank(message = "กรุณากรอกเขต/อำเภอ")
  private String district;

  @NotBlank(message = "กรุณากรอกจังหวัด")
  private String province;

  @NotBlank(message = "กรุณากรอกรหัสไปรษณีย์")
  @Pattern(regexp = "^[0-9]{5}$", message = "รหัสไปรษณีย์ต้องเป็นตัวเลข 5 หลัก")
  private String postalCode;

  @NotBlank(message = "Country is required")
  @Size(max = 100, message = "Country must not exceed 100 characters")
  String country;

  @Size(max = 100, message = "Label must not exceed 100 characters")
  private String label;

  private Boolean isDefault = false;

  public UUID getId() {
    return id;
  }

  public AddressWebFormDTO setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getFullName() {
    return fullName;
  }

  public AddressWebFormDTO setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public AddressWebFormDTO setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public AddressWebFormDTO setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
    return this;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public AddressWebFormDTO setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
    return this;
  }

  public String getSubDistrict() {
    return subDistrict;
  }

  public AddressWebFormDTO setSubDistrict(String subDistrict) {
    this.subDistrict = subDistrict;
    return this;
  }

  public String getDistrict() {
    return district;
  }

  public AddressWebFormDTO setDistrict(String district) {
    this.district = district;
    return this;
  }

  public String getProvince() {
    return province;
  }

  public AddressWebFormDTO setProvince(String province) {
    this.province = province;
    return this;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public AddressWebFormDTO setPostalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  public String getLabel() {
    return label;
  }

  public AddressWebFormDTO setLabel(String label) {
    this.label = label;
    return this;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public AddressWebFormDTO setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  public String getCountry() {
    return country;
  }

  public AddressWebFormDTO setCountry(String country) {
    this.country = country;
    return this;
  }
}
