package com.example.restservice.Products.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.restservice.Products.domain.Price;
import com.example.restservice.Products.domain.Product;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductModel {

  @Id private UUID id;

  @Column(length = 255, nullable = false, unique = true)
  private String productName;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal productPrice;

  @Column(length = 511)
  private String productDescription;

  @Column(nullable = false)
  private UUID createdBy;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant createdAt;

  @UpdateTimestamp private Instant updatedAt;

  protected ProductModel() {}

  public UUID getId() {
    return id;
  }

  public String getProductName() {
    return productName;
  }

  public BigDecimal getProductPrice() {
    return productPrice;
  }

  public String getProductDescription() {
    return productDescription;
  }

  public UUID getCreatedBy() {
    return createdBy;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Product toDomain() {
    return Product.rehydrate(
        this.id,
        this.productName,
        Price.of(this.productPrice),
        this.productDescription,
        this.createdBy,
        this.createdAt,
        this.updatedAt);
  }

  public static ProductModel fromDomain(Product product) {
    if (product == null) {
      return null;
    }

    ProductModel model = new ProductModel();

    model.id = product.getId();
    model.productName = product.getName();
    model.productPrice = product.getPrice().getValue();
    model.productDescription = product.getDescription();
    model.createdBy = product.getCreatedBy();
    model.createdAt = product.getCreatedAt();
    model.updatedAt = product.getUpdatedAt();

    return model;
  }
}
