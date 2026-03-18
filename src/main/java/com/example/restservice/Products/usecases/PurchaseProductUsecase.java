package com.example.restservice.Products.usecases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.domain.OrderAddress;
import com.example.restservice.Orders.domain.ProductSnapshot;
import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.exceptions.ProductNotFoundException;
import com.example.restservice.TransactionStatements.domain.DatabaseTransactionStatementsRepository;
import com.example.restservice.TransactionStatements.domain.TransactionStatement;
import com.example.restservice.TransactionStatements.domain.TransactionStatementFactory;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsStatus;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsType;
import com.example.restservice.Users.domain.Credit;
import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.User;
import com.example.restservice.Users.exceptions.UserNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class PurchaseProductUsecase {
  private final DatabaseUserRepository databaseUserRepository;
  private final DatabaseProductRepository databaseProductRepository;
  private final DatabaseAddressRepository databaseAddressRepository;
  private final DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository;
  private final DatabaseOrderRepository databaseOrderRepository;

  PurchaseProductUsecase(
      DatabaseUserRepository databaseUserRepository,
      DatabaseAddressRepository databaseAddressRepository,
      DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository,
      DatabaseOrderRepository databaseOrderRepository,
      DatabaseProductRepository databaseProductRepository) {
    this.databaseProductRepository = databaseProductRepository;
    this.databaseAddressRepository = databaseAddressRepository;
    this.databaseUserRepository = databaseUserRepository;
    this.databaseTransactionStatementsRepository = databaseTransactionStatementsRepository;
    this.databaseOrderRepository = databaseOrderRepository;
  }

  @Transactional
  public void execute(UUID userId, UUID productId, UUID addressId) {

    User user =
        databaseUserRepository
            .findUserByUserId(userId)
            .orElseThrow(() -> new UserNotFoundException("not found"));

    Product product =
        databaseProductRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("not found"));
    Address address = databaseAddressRepository.findById(addressId).orElseThrow();
    user.purchase(product.getPrice());
    Order order = Order.create(userId, ProductSnapshot.of(product), OrderAddress.of(address));
    TransactionStatement transactionStatements =
        TransactionStatementFactory.create(
            userId,
            Optional.ofNullable(order.getId()),
            Credit.of(product.getPrice().getValue()),
            TransactionStatementsType.PURCHASE,
            Optional.empty(),
            TransactionStatementsStatus.SUCCEED,
            Optional.empty());
    databaseOrderRepository.save(order);
    databaseTransactionStatementsRepository.save(transactionStatements);
    databaseUserRepository.save(user);
  }
}
