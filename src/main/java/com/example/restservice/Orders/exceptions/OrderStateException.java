package com.example.restservice.Orders.exceptions;

import com.example.restservice.Orders.domain.OrderStatus;

public class OrderStateException extends RuntimeException {

  private final OrderStatus currentStatus;
  private final OrderStatus expectedStatus;

  public OrderStateException(
      OrderStatus currentStatus,
      OrderStatus expectedStatus,
      String message) {

    super(message);
    this.currentStatus = currentStatus;
    this.expectedStatus = expectedStatus;
  }

  public OrderStatus getCurrentStatus() {
    return currentStatus;
  }

  public OrderStatus getExpectedStatus() {
    return expectedStatus;
  }
}
