package com.example.restservice.TransactionStatements.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.TransactionStatements.domain.DatabaseTransactionStatementsRepository;
import com.example.restservice.TransactionStatements.domain.TransactionStatements;
import com.example.restservice.TransactionStatements.dto.CreateTransactionStatementsRequestDTO;
import com.example.restservice.TransactionStatements.dto.CreateTransactionStatementsResponseDTO;
import com.example.restservice.TransactionStatements.execeptions.UserNotFoundException;
import com.example.restservice.Users.repositories.JpaUserRepository;

import jakarta.transaction.Transactional;

@Service
public class CreateTransactionStatementsUsecase {
  private final DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository;
  private final JpaUserRepository jpaUserRepository;

  CreateTransactionStatementsUsecase(
      DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository,
      JpaUserRepository jpaUserRepository) {
    this.databaseTransactionStatementsRepository = databaseTransactionStatementsRepository;
    this.jpaUserRepository = jpaUserRepository;
  }

  @Transactional
  public CreateTransactionStatementsResponseDTO execute(
      CreateTransactionStatementsRequestDTO request) {

    if (!jpaUserRepository.existsById(request.userId())) {
      throw new UserNotFoundException("User with ID " + request.userId() + " not found");
    }

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
