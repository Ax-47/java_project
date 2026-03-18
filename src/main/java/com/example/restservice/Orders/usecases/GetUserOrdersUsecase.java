package com.example.restservice.Orders.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.dto.OrderResponseDTO;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

@Service
public class GetUserOrdersUsecase {

  private final DatabaseOrderRepository databaseOrderRepository;

  public GetUserOrdersUsecase(DatabaseOrderRepository databaseOrderRepository) {
    this.databaseOrderRepository = databaseOrderRepository;
  }

  public Page<OrderResponseDTO> execute(UUID userId, PageQuery query) {
    Page<Order> ordersPage = databaseOrderRepository.findAllByUserId(userId, query);

    List<OrderResponseDTO> content =
        ordersPage.content().stream().map(OrderResponseDTO::from).toList();
    return new Page<>(
        content,
        ordersPage.totalElements(),
        ordersPage.totalPages(),
        ordersPage.page(),
        ordersPage.size());
  }
}
