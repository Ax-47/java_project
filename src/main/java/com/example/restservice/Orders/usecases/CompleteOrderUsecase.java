package com.example.restservice.Orders.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.dto.*;
import com.example.restservice.Orders.exceptions.OrderNotFoundException;
import com.example.restservice.Products.exceptions.UnauthorizedProductActionException;

import jakarta.transaction.Transactional;

@Service
public class CompleteOrderUsecase {
  private final DatabaseOrderRepository databaseOrderRepository;

  public CompleteOrderUsecase(DatabaseOrderRepository databaseOrderRepository) {
    this.databaseOrderRepository = databaseOrderRepository;
  }

  @Transactional
  public CompleteOrderResponseDTO execute(CompleteOrderRequestDTO request) {
    Order existingOrder =
        this.databaseOrderRepository
            .findById(request.orderId())
            .orElseThrow(
                () -> new OrderNotFoundException("Order not = with ID: " + request.orderId()));

    if (!existingOrder.getUserId().equals(request.userId())) {
      throw new UnauthorizedProductActionException("Unauthorized to cancel this order");
    }

    existingOrder.complete();

    this.databaseOrderRepository.save(existingOrder);

    return new CompleteOrderResponseDTO("Delivery completed");
  }
}
