package com.example.restservice.TransactionStatements.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.example.restservice.TransactionStatements.execeptions.TransactionValidationException;

public class TransactionStatements {

  private final UUID id;
  private final UUID userId;
  private final UUID orderId; // nullable
  private final Double amount;

  private final TransactionStatementsType type;
  private final TransactionStatementsMethod method;
  private final TransactionStatementsStatus status;

  private final String referenceId;
  private final LocalDateTime createdAt;

  public TransactionStatements(
      UUID id,
      UUID userId,
      UUID orderId,
      Double amount,
      TransactionStatementsType type,
      TransactionStatementsMethod method,
      TransactionStatementsStatus status,
      String referenceId,
      LocalDateTime createdAt) {

    this.id = Objects.requireNonNull(id, "id is required");
    this.userId = Objects.requireNonNull(userId, "userId is required");
    this.orderId = orderId;
    this.amount = Objects.requireNonNull(amount, "amount is required");

    this.type = Objects.requireNonNull(type, "type is required");
    this.method = Objects.requireNonNull(method, "method is required");
    this.status = Objects.requireNonNull(status, "status is required");

    this.referenceId = require("referenceId", referenceId, "Reference ID is required");
    this.createdAt = Objects.requireNonNull(createdAt, "createdAt is required");
  }

  public static TransactionStatements create(
      UUID userId,
      UUID orderId,
      Double amount,
      TransactionStatementsType type,
      TransactionStatementsMethod method,
      TransactionStatementsStatus status,
      String referenceId) {

    return new TransactionStatements(
        UUID.randomUUID(),
        userId,
        orderId,
        amount,
        type,
        method,
        status,
        referenceId,
        LocalDateTime.now());
  }

  public static TransactionStatements rehydrate(
      UUID id,
      UUID userId,
      UUID orderId,
      Double amount,
      TransactionStatementsType type,
      TransactionStatementsMethod method,
      TransactionStatementsStatus status,
      String referenceId,
      LocalDateTime createdAt) {

    return new TransactionStatements(
        id, userId, orderId, amount, type, method, status, referenceId, createdAt);
  }

  private String require(String field, String value, String message) {
    if (value == null || value.isBlank()) {
      throw new TransactionValidationException(field, message);
    }
    return value;
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public Double getAmount() {
    return amount;
  }

  public TransactionStatementsType getType() {
    return type;
  }

  public TransactionStatementsMethod getMethod() {
    return method;
  }

  public TransactionStatementsStatus getStatus() {
    return status;
  }

  public String getReferenceId() {
    return referenceId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
