package com.example.restservice.TransactionStatements.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.example.restservice.Users.domain.Credit;

public class PurchaseStatement extends TransactionStatement {

  private final UUID orderId;

  public PurchaseStatement(
      UUID id,
      UUID userId,
      UUID orderId,
      Credit amount,
      TransactionStatementsStatus status,
      LocalDateTime createdAt) {

    super(id, userId, amount, status, createdAt);
    this.orderId =
        Objects.requireNonNull(orderId, "Order ID is required for purchase transactions");
  }

  @Override
  public Optional<TransactionStatementsMethod> getMethod() {
    return Optional.empty();
  }

  @Override
  public Optional<UUID> getOrderId() {
    return Optional.of(orderId);
  }

  @Override
  public TransactionStatementsType getType() {
    return TransactionStatementsType.PURCHASE;
  }

  @Override
  public Optional<String> getReferenceId() {
    return Optional.empty();
  }
}
