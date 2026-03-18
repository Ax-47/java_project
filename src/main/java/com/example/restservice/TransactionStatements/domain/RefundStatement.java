package com.example.restservice.TransactionStatements.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.example.restservice.Users.domain.Credit;

public class RefundStatement extends TransactionStatement {

  private final UUID orderId;

  public RefundStatement(
      UUID id,
      UUID userId,
      UUID orderId,
      Credit amount,
      TransactionStatementsStatus status,
      LocalDateTime createdAt) {

    super(id, userId, amount, status, createdAt);
    this.orderId = Objects.requireNonNull(orderId, "Order ID is required for refund transactions");
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
    return TransactionStatementsType.REFUND;
  }

  @Override
  public Optional<String> getReferenceId() {
    return Optional.empty();
  }
}
