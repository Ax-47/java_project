package com.example.restservice.Products.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

  @Id private UUID categoryId;

  @Column(nullable = false)
  private UUID createdBy;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public Product toDomain() {
    return Product.rehydrate(
        this.id,
        this.productName,
        this.productPrice,
        this.productDescription,
        this.categoryId,
        this.createdBy,
        this.createdAt,
        this.updatedAt);
  }

  public static ProductModel fromDomain(Product product) {
    if (product == null) {
      return null;
    }

    ProductModel model = new ProductModel();

    if (product.getId() != null) {
      model.id = product.getId();
    }

    model.productName = product.getName();
    model.productPrice = product.getPrice().getValue();
    model.productDescription = product.getDescription();
    model.createdBy = product.getCreatedBy();

    return model;
  }
}
