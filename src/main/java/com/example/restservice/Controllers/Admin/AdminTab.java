package com.example.restservice.Controllers.Admin;

public enum AdminTab {
  ORDERS("orders"),
  PRODUCTS("products"),
  CATEGORIES("categories");

  private final String value;

  AdminTab(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static AdminTab fromString(String text) {
    for (AdminTab tab : AdminTab.values()) {
      if (tab.value.equalsIgnoreCase(text)) {
        return tab;
      }
    }
    return ORDERS;
  }
}
