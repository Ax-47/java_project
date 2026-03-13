package com.example.restservice.TransactionStatements.dto;

import java.util.UUID;

import com.example.restservice.TransactionStatements.domain.TransactionStatementsMethod;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsStatus;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateTransactionStatementsRequestDTO(
    @NotNull(message = "User ID is required") UUID userId,
    UUID orderId,
    @NotNull(message = "Amount is required") @Positive(message = "Amount must be greater than zero")
        Double amount,
    @NotNull(message = "Transaction type is required") TransactionStatementsType type,
    @NotNull(message = "Transaction method is required") TransactionStatementsMethod method,
    @NotNull(message = "Transaction status is required") TransactionStatementsStatus status,
    @NotBlank(message = "Reference ID is required")
        @Size(max = 255, message = "Reference ID must not exceed 255 characters")
        String referenceId) {}
