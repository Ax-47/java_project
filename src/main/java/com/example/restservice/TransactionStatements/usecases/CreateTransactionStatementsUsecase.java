package com.example.restservice.TransactionStatements.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.restservice.TransactionStatements.domain.DatabaseTransactionStatementsRepository;
import com.example.restservice.TransactionStatements.domain.TransactionStatement;
import com.example.restservice.TransactionStatements.domain.TransactionStatementFactory;
import com.example.restservice.TransactionStatements.dto.CreateTransactionStatementsRequestDTO;
import com.example.restservice.TransactionStatements.dto.CreateTransactionStatementsResponseDTO;
import com.example.restservice.TransactionStatements.execeptions.UserNotFoundException;
import com.example.restservice.Users.domain.Credit;
import com.example.restservice.Users.domain.DatabaseUserRepository;

import jakarta.transaction.Transactional;

@Service
public class CreateTransactionStatementsUsecase {
  private final DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository;
  private final DatabaseUserRepository databaseUserRepository;

  CreateTransactionStatementsUsecase(
      DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository,
      DatabaseUserRepository databaseUserRepository) {
    this.databaseTransactionStatementsRepository = databaseTransactionStatementsRepository;
    this.databaseUserRepository = databaseUserRepository;
  }

  @Transactional
  public CreateTransactionStatementsResponseDTO execute(
      CreateTransactionStatementsRequestDTO request) {

    if (!databaseUserRepository.existsByUserId(request.userId())) {
      throw new UserNotFoundException("User with ID " + request.userId() + " not found");
    }

    TransactionStatement newTransaction =
        TransactionStatementFactory.create(
            request.userId(),
            Optional.ofNullable(request.orderId()),
            Credit.of(request.amount()),
            request.type(),
            Optional.ofNullable(request.method()),
            request.status(),
            Optional.ofNullable(request.referenceId()));

    this.databaseTransactionStatementsRepository.save(newTransaction);

    return new CreateTransactionStatementsResponseDTO(
        "Transaction statement was created successfully");
  }
}
