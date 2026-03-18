package com.example.restservice.Orders.domain;

public enum OrderSortField {
  id("id"),
  fullName("fullName"),
  productName("productName"),
  status("status"),
  createdAt("createdAt");

  private final String field;

  OrderSortField(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }
}
