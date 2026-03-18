package com.example.restservice.Address.models;

public enum AddressSortField {
  FULL_NAME("fullName"),
  PROVINCE("province"),
  LABEL("label"),
  IS_DEFAULT("isDefault"),
  CREATED_AT("createdAt"),
  UPDATED_AT("updatedAt");

  private final String fieldName;

  AddressSortField(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public static AddressSortField fromString(String value) {
    if (value == null) return UPDATED_AT;

    for (AddressSortField field : AddressSortField.values()) {
      if (field.getFieldName().equalsIgnoreCase(value)) {
        return field;
      }
    }
    return UPDATED_AT;
  }
}
