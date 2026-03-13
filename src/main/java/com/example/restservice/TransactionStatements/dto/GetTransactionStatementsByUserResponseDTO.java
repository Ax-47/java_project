package com.example.restservice.TransactionStatements.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.restservice.TransactionStatements.domain.TransactionStatements;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsMethod;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsStatus;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsType;

public record GetTransactionStatementsByUserResponseDTO(
    UUID id,
    UUID userId,
    UUID orderId,
    Double amount,
    TransactionStatementsType type,
    TransactionStatementsMethod method,
    TransactionStatementsStatus status,
    String referenceId,
    LocalDateTime createdAt) {

  public static GetTransactionStatementsByUserResponseDTO from(TransactionStatements statement) {
    if (statement == null) return null;

    return new GetTransactionStatementsByUserResponseDTO(
        statement.getId(),
        statement.getUserId(),
        statement.getOrderId(),
        statement.getAmount(),
        statement.getType(),
        statement.getMethod(),
        statement.getStatus(),
        statement.getReferenceId(),
        statement.getCreatedAt());
  }
}
