package com.example.restservice.Orders.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.dto.OrderResponseDTO;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

@Service
public class GetOrdersUsecase {

  private final DatabaseOrderRepository databaseOrderRepository;

  public GetOrdersUsecase(DatabaseOrderRepository databaseOrderRepository) {
    this.databaseOrderRepository = databaseOrderRepository;
  }

  public Page<OrderResponseDTO> execute(PageQuery query) {
    Page<Order> ordersPage = databaseOrderRepository.findAll(query);
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
