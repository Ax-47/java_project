package com.example.restservice.TransactionStatements.repositories;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restservice.TransactionStatements.models.TransactionStatementsModel;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TransactionStatementsModel, UUID> {
  org.springframework.data.domain.Page<TransactionStatementsModel> findByUserId(
      UUID userId, Pageable pageable);
}
