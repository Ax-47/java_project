package com.example.restservice.TransactionStatements.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.example.restservice.Users.domain.Credit;

public class PurchaseStatement extends TransactionStatement {

  private final UUID orderId;

  public PurchaseStatement(
      UUID id,
      UUID userId,
      UUID orderId,
      Credit amount,
      TransactionStatementsMethod method,
      TransactionStatementsStatus status,
      String referenceId,
      LocalDateTime createdAt) {

    super(id, userId, amount, method, status, referenceId, createdAt);
    this.orderId =
        Objects.requireNonNull(orderId, "Order ID is required for purchase transactions");
  }

  public UUID getOrderId() {
    return orderId;
  }

  @Override
  public TransactionStatementsType getType() {
    return TransactionStatementsType.PURCHASE;
  }
}
