package com.example.restservice.Orders.domain;

import java.util.Objects;

import com.example.restservice.Products.domain.Price;

public class ProductSnapshot {

  private final Long productId;
  private final String productName;
  private final Price price;

  public ProductSnapshot(Long productId, String productName, Price price) {

    this.productId = Objects.requireNonNull(productId);

    if (productName == null || productName.isBlank())
      throw new IllegalArgumentException("Product name required");

    this.productName = productName;
    this.price = Objects.requireNonNull(price);
  }

  public Long getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public Price getPrice() {
    return price;
  }
}
