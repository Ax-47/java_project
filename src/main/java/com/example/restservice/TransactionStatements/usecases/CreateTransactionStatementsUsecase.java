package com.example.restservice.TransactionStatements.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.TransactionStatements.domain.DatabaseTransactionStatementsRepository;
import com.example.restservice.TransactionStatements.domain.TransactionStatements;
import com.example.restservice.TransactionStatements.dto.CreateTransactionStatementsRequestDTO;
import com.example.restservice.TransactionStatements.dto.CreateTransactionStatementsResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class CreateTransactionStatementsUsecase {
  private final DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository;

  CreateTransactionStatementsUsecase(
      DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository) {
    this.databaseTransactionStatementsRepository = databaseTransactionStatementsRepository;
  }

  @Transactional
  public CreateTransactionStatementsResponseDTO execute(
      CreateTransactionStatementsRequestDTO request) {

    TransactionStatements newTransaction =
        TransactionStatements.create(
            request.userId(),
            request.orderId(),
            request.amount(),
            request.type(),
            request.method(),
            request.status(),
            request.referenceId());

    this.databaseTransactionStatementsRepository.save(newTransaction);

    return new CreateTransactionStatementsResponseDTO(
        "Transaction statement was created successfully");
  }
}
