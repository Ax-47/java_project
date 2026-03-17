package com.example.restservice.TransactionStatements.domain;

import java.util.UUID;

import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

public interface DatabaseTransactionStatementsRepository {
  public TransactionStatement save(TransactionStatement transactionStatements);

  public Page<TransactionStatement> findByUserId(UUID userId, PageQuery pageable);
}
