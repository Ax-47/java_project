package com.example.restservice.Users.usecases;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.TransactionStatements.domain.DatabaseTransactionStatementsRepository;
import com.example.restservice.TransactionStatements.domain.TransactionStatement;
import com.example.restservice.TransactionStatements.domain.TransactionStatementFactory;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsMethod;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsStatus;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsType;
import com.example.restservice.Users.domain.*;
import com.example.restservice.Users.dto.*;

import jakarta.transaction.Transactional;

@Service
public class TopupCreditUsecase {

  private final DatabaseUserRepository databaseUserRepository;
  private final DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository;

  public TopupCreditUsecase(
      DatabaseUserRepository databaseUserRepository,
      DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository) {
    this.databaseUserRepository = databaseUserRepository;
    this.databaseTransactionStatementsRepository = databaseTransactionStatementsRepository;
  }

  @Transactional
  public CreateUserResponseDTO execute(
      UUID userId, BigDecimal amount, TransactionStatementsMethod method) {
    User user = databaseUserRepository.findUserByUserId(userId).orElseThrow();
    Credit amontCredit = Credit.of(amount);
    user.topup(amontCredit);
    TransactionStatement transactionStatements =
        TransactionStatementFactory.create(
            userId,
            Optional.empty(),
            amontCredit,
            TransactionStatementsType.TOPUP,
            Optional.ofNullable(method),
            TransactionStatementsStatus.SUCCEED,
            Optional.ofNullable(UUID.randomUUID().toString())); // assume it's prompt_pay's ref_id
    databaseUserRepository.save(user);
    databaseTransactionStatementsRepository.save(transactionStatements);
    return new CreateUserResponseDTO("User has been created");
  }
}
