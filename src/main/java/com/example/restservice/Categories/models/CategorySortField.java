package com.example.restservice.Categories.models;

public enum CategorySortField {
  categoryName("categoryName"),
  createdAt("createdAt"),
  updatedAt("updatedAt");

  private final String field;

  CategorySortField(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }
}
