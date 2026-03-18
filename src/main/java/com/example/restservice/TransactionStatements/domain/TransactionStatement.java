package com.example.restservice.TransactionStatements.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.example.restservice.TransactionStatements.execeptions.TransactionValidationException;
import com.example.restservice.Users.domain.Credit;

public abstract class TransactionStatement {

  private final UUID id;
  private final UUID userId;
  private final Credit amount;
  private final TransactionStatementsStatus status;
  private final LocalDateTime createdAt;

  protected TransactionStatement(
      UUID id,
      UUID userId,
      Credit amount,
      TransactionStatementsStatus status,
      LocalDateTime createdAt) {

    this.id = Objects.requireNonNull(id, "id is required");
    this.userId = Objects.requireNonNull(userId, "userId is required");
    this.amount = Objects.requireNonNull(amount, "amount is required");
    this.status = Objects.requireNonNull(status, "status is required");
    this.createdAt = Objects.requireNonNull(createdAt, "createdAt is required");
  }

  private String requireNonBlank(String field, String value, String message) {
    if (value == null || value.isBlank()) {
      throw new TransactionValidationException(field, message);
    }
    return value;
  }

  public abstract TransactionStatementsType getType();

  public abstract Optional<TransactionStatementsMethod> getMethod();

  public abstract Optional<String> getReferenceId();

  public abstract Optional<UUID> getOrderId();

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public Credit getAmount() {
    return amount;
  }

  public TransactionStatementsStatus getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
