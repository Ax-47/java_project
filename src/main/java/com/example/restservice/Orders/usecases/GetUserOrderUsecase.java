package com.example.restservice.Orders.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.dto.OrderResponseDTO;

@Service
public class GetUserOrderUsecase {

  private final DatabaseOrderRepository databaseOrderRepository;

  public GetUserOrderUsecase(DatabaseOrderRepository databaseOrderRepository) {
    this.databaseOrderRepository = databaseOrderRepository;
  }

  public OrderResponseDTO execute(UUID orderId) {
    Order order = databaseOrderRepository.findById(orderId).orElseThrow();
    return OrderResponseDTO.from(order);
  }
}
