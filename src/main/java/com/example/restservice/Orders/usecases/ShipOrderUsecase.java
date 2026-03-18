package com.example.restservice.Orders.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.dto.*;
import com.example.restservice.Orders.exceptions.OrderNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class ShipOrderUsecase {
  private final DatabaseOrderRepository databaseOrderRepository;

  public ShipOrderUsecase(DatabaseOrderRepository databaseOrderRepository) {
    this.databaseOrderRepository = databaseOrderRepository;
  }

  @Transactional
  public CompleteOrderResponseDTO execute(UUID orderId) {
    Order existingOrder =
        this.databaseOrderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order not = with ID: " + orderId));

    existingOrder.ship();

    this.databaseOrderRepository.save(existingOrder);

    return new CompleteOrderResponseDTO("Delivery completed");
  }
}
