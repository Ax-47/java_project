package com.example.restservice.Orders.exceptions;

import com.example.restservice.Orders.domain.OrderStatus;

public class OrderCancellationNotAllowedException extends RuntimeException {

  private final OrderStatus currentStatus;

  public OrderCancellationNotAllowedException(OrderStatus currentStatus) {
    super("Cannot cancel order with status: " + currentStatus);
    this.currentStatus = currentStatus;
  }

  public OrderStatus getCurrentStatus() {
    return currentStatus;
  }
}
