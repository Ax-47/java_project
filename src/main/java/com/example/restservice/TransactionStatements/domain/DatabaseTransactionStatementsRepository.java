package com.example.restservice.TransactionStatements.domain;

import java.util.UUID;

import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

public interface DatabaseTransactionStatementsRepository {
  public TransactionStatements save(TransactionStatements transactionStatements);

  public Page<TransactionStatements> findByUserId(UUID userId, PageQuery pageable);
}
