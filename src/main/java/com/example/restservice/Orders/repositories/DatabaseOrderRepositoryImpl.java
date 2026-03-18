package com.example.restservice.Orders.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.models.OrderModel;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

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

  public Page<Order> findAllByUserId(UUID userId, PageQuery query) {
    Sort sort =
        query.ascending()
            ? Sort.by(query.sortBy()).ascending()
            : Sort.by(query.sortBy()).descending();
    Pageable pageable = PageRequest.of(query.page(), query.size(), sort);

    var page = jpaOrderRepository.findAllByUserId(userId, pageable);
    List<Order> products = page.getContent().stream().map(product -> product.toDomain()).toList();
    return new Page<>(
        products, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
  }

  @Override
  public Page<Order> findAll(PageQuery query) {

    Sort sort =
        query.ascending()
            ? Sort.by(query.sortBy()).ascending()
            : Sort.by(query.sortBy()).descending();
    Pageable pageable = PageRequest.of(query.page(), query.size(), sort);

    var page = jpaOrderRepository.findAll(pageable);
    List<Order> products = page.getContent().stream().map(product -> product.toDomain()).toList();
    return new Page<>(
        products, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
  }

  @Override
  public Order delete(Order order) {
    jpaOrderRepository.deleteById(order.getId());
    return order;
  }
}
