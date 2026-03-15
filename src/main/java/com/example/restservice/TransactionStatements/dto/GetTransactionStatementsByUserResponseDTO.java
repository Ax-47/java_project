package com.example.restservice.TransactionStatements.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.restservice.TransactionStatements.domain.TransactionStatement;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsMethod;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsStatus;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsType;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetTransactionStatementsByUserResponseDTO(
    UUID id,
    UUID userId,
    UUID orderId,
    BigDecimal amount,
    TransactionStatementsType type,
    TransactionStatementsMethod method,
    TransactionStatementsStatus status,
    String referenceId,
    LocalDateTime createdAt) {

  public static GetTransactionStatementsByUserResponseDTO from(TransactionStatement statement) {
    if (statement == null) return null;

    return new GetTransactionStatementsByUserResponseDTO(
        statement.getId(),
        statement.getUserId(),
        statement.getOrderId().orElse(null),
        statement.getAmount().getValue(),
        statement.getType(),
        statement.getMethod(),
        statement.getStatus(),
        statement.getReferenceId(),
        statement.getCreatedAt());
  }
}
