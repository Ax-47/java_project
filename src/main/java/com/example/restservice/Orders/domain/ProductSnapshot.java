package com.example.restservice.Orders.domain;

import java.util.Objects;
import java.util.UUID;

import com.example.restservice.Products.domain.Price;

public class ProductSnapshot {

  private final UUID productId;
  private final String productName;
  private final Price price;

  public ProductSnapshot(UUID productId, String productName, Price price) {

    this.productId = Objects.requireNonNull(productId);

    if (productName == null || productName.isBlank())
      throw new IllegalArgumentException("Product name required");

    this.productName = productName;
    this.price = Objects.requireNonNull(price);
  }

  public UUID getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public Price getPrice() {
    return price;
  }
}
