package com.example.restservice.Products.usecases;

import java.util.UUID;

import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.exceptions.ProductNotFoundException;
import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.User;
import com.example.restservice.Users.exceptions.UserNotFoundException;

public class PurchaseProductUsecase {
  private final DatabaseUserRepository databaseUserRepository;
  private final DatabaseProductRepository databaseProductRepository;

  PurchaseProductUsecase(
      DatabaseUserRepository databaseUserRepository,
      DatabaseProductRepository databaseProductRepository) {
    this.databaseProductRepository = databaseProductRepository;
    this.databaseUserRepository = databaseUserRepository;
  }

  public void execute(UUID userId, UUID productId) {

    User user =
        databaseUserRepository
            .findUserByUserId(userId)
            .orElseThrow(() -> new UserNotFoundException("not found"));

    Product product =
        databaseProductRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("not found"));

    user.purchase(product.getPrice());

    databaseUserRepository.save(user);
  }
}
