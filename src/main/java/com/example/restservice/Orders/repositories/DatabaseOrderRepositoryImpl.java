package com.example.restservice.Orders.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.models.OrderModel;

@Repository
public class DatabaseOrderRepositoryImpl implements DatabaseOrderRepository {

  private final JpaOrderRepository jpaOrderRepository;

  public DatabaseOrderRepositoryImpl(JpaOrderRepository jpaOrderRepository) {
    this.jpaOrderRepository = jpaOrderRepository;
  }

  @Override
  public Order save(Order order) {
    OrderModel model = OrderModel.fromDomain(order);
    OrderModel saved = jpaOrderRepository.save(model);
    return saved.toDomain();
  }

  @Override
  public Optional<Order> findById(UUID id) {
    return jpaOrderRepository.findById(id).map(OrderModel::toDomain);
  }

  @Override
  public Order delete(Order order) {
    jpaOrderRepository.deleteById(order.getId());
    return order;
  }
}
