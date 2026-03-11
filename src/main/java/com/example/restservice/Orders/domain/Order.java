package com.example.restservice.Orders.domain;

import com.example.restservice.Orders.exceptions.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {

  private final UUID id;
  private final UUID userId;

  private final ProductSnapshot product;
  private final OrderAddress shippingAddress;

  private OrderStatus status;

  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Order(
      UUID id,
      UUID userId,
      ProductSnapshot product,
      OrderAddress shippingAddress,
      OrderStatus status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {

    this.id = id;
    this.userId = Objects.requireNonNull(userId);
    this.product = Objects.requireNonNull(product);
    this.shippingAddress = Objects.requireNonNull(shippingAddress);
    this.status = Objects.requireNonNull(status);
    this.createdAt = Objects.requireNonNull(createdAt);
    this.updatedAt = Objects.requireNonNull(updatedAt);
  }

  public static Order create(
      UUID userId,
      ProductSnapshot product,
      OrderAddress shippingAddress) {
    return new Order(
        null,
        userId,
        product,
        shippingAddress,
        OrderStatus.PENDING,
        LocalDateTime.now(),
        LocalDateTime.now());
  }

  public static Order rehydrate(
      UUID id,
      UUID userId,
      ProductSnapshot snap_product,
      OrderAddress shippingAddress,
      OrderStatus status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt

  ) {
    return new Order(
        id,
        userId,
        snap_product,
        shippingAddress,
        status,
        createdAt,
        updatedAt);
  }

  public void ship() {
    this.status = OrderStatus.SHIPPED;
    touch();
  }

  public void complete() {
    ensureStatus(OrderStatus.SHIPPED);
    this.status = OrderStatus.COMPLETED;
    touch();
  }

  public void cancel() {
    if (this.status == OrderStatus.SHIPPED ||
        this.status == OrderStatus.COMPLETED)
      throw new OrderCancellationNotAllowedException(this.status);

    this.status = OrderStatus.CANCELLED;
    touch();
  }

  private void ensureStatus(OrderStatus expected) {
    if (this.status != expected)
      throw new OrderStateException(
          this.status,
          expected,
          "Invalid transition from " + this.status +
              " (expected: " + expected + ")");
  }

  private void touch() {
    this.updatedAt = LocalDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public ProductSnapshot getProduct() {
    return product;
  }

  public OrderAddress getShippingAddress() {
    return shippingAddress;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
