package com.example.restservice.Orders.domain;

import java.util.Optional;
import java.util.UUID;

import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

public interface DatabaseOrderRepository {
  public Order save(Order product);

  Optional<Order> findById(UUID id);

  Page<Order> findAllByUserId(UUID userId, PageQuery query);

  Page<Order> findAll(PageQuery query);

  public Order delete(Order product);
}
