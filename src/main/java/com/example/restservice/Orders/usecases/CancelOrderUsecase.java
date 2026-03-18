package com.example.restservice.Orders.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.dto.CancelOrderResponseDTO;
import com.example.restservice.Orders.exceptions.OrderNotFoundException;

@Service
public class CancelOrderUsecase {

  private final DatabaseOrderRepository databaseOrderRepository;

  public CancelOrderUsecase(DatabaseOrderRepository databaseOrderRepository) {
    this.databaseOrderRepository = databaseOrderRepository;
  }

  @Transactional
  public CancelOrderResponseDTO execute(UUID orderId) {
    Order existingOrder =
        this.databaseOrderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

    existingOrder.cancel();

    this.databaseOrderRepository.save(existingOrder);

    return new CancelOrderResponseDTO("Order was cancelled successfully");
  }
}
