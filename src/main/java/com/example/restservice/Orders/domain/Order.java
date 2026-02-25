package com.example.restservice.Orders.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import com.example.restservice.Orders.exceptions.*;

public class Order {

  private final Long id;
  private final Long userId;

  private final ProductSnapshot product;
  private final OrderAddress shippingAddress;

  private OrderStatus status;

  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Order(
      Long id,
      Long userId,
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
      Long userId,
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

  public Long getId() {
    return id;
  }

  public Long getUserId() {
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
