package com.example.restservice.Orders.usecases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.dto.CancelOrderResponseDTO;
import com.example.restservice.Orders.exceptions.OrderNotFoundException;
import com.example.restservice.TransactionStatements.domain.DatabaseTransactionStatementsRepository;
import com.example.restservice.TransactionStatements.domain.TransactionStatement;
import com.example.restservice.TransactionStatements.domain.TransactionStatementFactory;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsStatus;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsType;
import com.example.restservice.Users.domain.Credit;
import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.User;

@Service
public class CancelOrderUsecase {

  private final DatabaseOrderRepository databaseOrderRepository;
  private final DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository;
  private final DatabaseUserRepository databaseUserRepository;

  public CancelOrderUsecase(
      DatabaseOrderRepository databaseOrderRepository,
      DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository,
      DatabaseUserRepository databaseUserRepository) {
    this.databaseOrderRepository = databaseOrderRepository;
    this.databaseUserRepository = databaseUserRepository;
    this.databaseTransactionStatementsRepository = databaseTransactionStatementsRepository;
  }

  @Transactional
  public CancelOrderResponseDTO execute(UUID orderId) {
    Order existingOrder =
        this.databaseOrderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

    User user = databaseUserRepository.findUserByUserId(existingOrder.getUserId()).orElseThrow();
    if (!existingOrder.getUserId().equals(user.getId()) && !user.isAdmin()) {
      throw new OrderNotFoundException("not found");
    }
    existingOrder.cancel();

    Credit amontCredit = Credit.of(existingOrder.getProduct().getPrice().getValue());
    user.topup(amontCredit);

    TransactionStatement transactionStatements =
        TransactionStatementFactory.create(
            existingOrder.getUserId(),
            Optional.ofNullable(existingOrder.getId()),
            amontCredit,
            TransactionStatementsType.REFUND,
            Optional.empty(),
            TransactionStatementsStatus.SUCCEED,
            Optional.empty());

    databaseOrderRepository.save(existingOrder);
    databaseUserRepository.save(user);
    databaseTransactionStatementsRepository.save(transactionStatements);
    return new CancelOrderResponseDTO("Order was cancelled successfully");
  }
}
