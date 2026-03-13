package com.example.restservice.TransactionStatements.dto;

import java.util.UUID;

import com.example.restservice.common.PageQuery;

import jakarta.validation.constraints.NotNull;

public record GetTransactionStatementsByUserRequestDTO(
    @NotNull(message = "User ID is required") UUID userId, PageQuery pageQuery) {}
