package com.example.restservice.TransactionStatements.domain;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.example.restservice.Users.domain.Credit;

public class TopUpStatement extends TransactionStatement {

  private final TransactionStatementsMethod method;
  private final String referenceId;

  public TopUpStatement(
      UUID id,
      UUID userId,
      Credit amount,
      TransactionStatementsMethod method,
      TransactionStatementsStatus status,
      String referenceId,
      LocalDateTime createdAt) {

    super(id, userId, amount, status, createdAt);
    this.method = method;
    this.referenceId = referenceId;
  }

  @Override
  public TransactionStatementsType getType() {
    return TransactionStatementsType.TOPUP;
  }

  @Override
  public Optional<TransactionStatementsMethod> getMethod() {
    return Optional.ofNullable(method);
  }

  @Override
  public Optional<UUID> getOrderId() {
    return Optional.empty();
  }

  @Override
  public Optional<String> getReferenceId() {
    return Optional.ofNullable(referenceId);
  }
}
