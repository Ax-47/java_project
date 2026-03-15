package com.example.restservice.TransactionStatements.domain;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.example.restservice.Users.domain.Credit;

public class TopUpStatement extends TransactionStatement {

  public TopUpStatement(
      UUID id,
      UUID userId,
      Credit amount,
      TransactionStatementsMethod method,
      TransactionStatementsStatus status,
      String referenceId,
      LocalDateTime createdAt) {

    super(id, userId, amount, method, status, referenceId, createdAt);
  }

  @Override
  public TransactionStatementsType getType() {
    return TransactionStatementsType.TOPUP;
  }
  @Override
  public Optional<UUID> getOrderId() {
    return Optional.empty();
  }
}
