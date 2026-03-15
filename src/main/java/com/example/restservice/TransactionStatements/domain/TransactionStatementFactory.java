package com.example.restservice.TransactionStatements.domain;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.example.restservice.TransactionStatements.execeptions.TransactionValidationException;
import com.example.restservice.Users.domain.Credit;

public class TransactionStatementFactory {

    public static TransactionStatement create(
            UUID userId,
            Optional<UUID> orderId,
            Credit amount,
            TransactionStatementsType type,
            TransactionStatementsMethod method,
            TransactionStatementsStatus status,
            String referenceId) {

        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        return switch (type) {
            case PURCHASE ->
                new PurchaseStatement(
                        id,
                        userId,
                        orderId.orElseThrow(
                                () -> new TransactionValidationException(
                                        "orderId", "Order ID is required for PURCHASE")),
                        amount,
                        method,
                        status,
                        referenceId,
                        now);

            case TOPUP -> {
                if (orderId.isPresent()) {
                    throw new TransactionValidationException(
                            "orderId", "Order ID must be null for top-up transactions");
                }
                yield new TopUpStatement(id, userId, amount, method, status, referenceId, now);
            }
            default -> throw new IllegalArgumentException("Unsupported transaction type: " + type);
        };
    }

    public static TransactionStatement rehydrate(
            UUID id,
            UUID userId,
            Optional<UUID> orderId,
            Credit amount,
            TransactionStatementsType type,
            TransactionStatementsMethod method,
            TransactionStatementsStatus status,
            String referenceId,
            LocalDateTime createdAt) {

        return switch (type) {
            case PURCHASE ->
                new PurchaseStatement(
                        id,
                        userId,
                        orderId.orElseThrow(
                                () -> new IllegalStateException("Corrupted data: PURCHASE missing Order ID")),
                        amount,
                        method,
                        status,
                        referenceId,
                        createdAt);

            case TOPUP -> {
                if (orderId.isPresent()) {
                    throw new IllegalStateException(
                            "Corrupted data: TOPUP transaction " + id + " has unexpected orderId");
                }
                yield new TopUpStatement(id, userId, amount, method, status, referenceId, createdAt);
            }

            default ->
                throw new IllegalStateException("Corrupted data: Unknown transaction type: " + type);
        };
    }
}
