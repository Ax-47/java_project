package com.example.restservice.Orders.domain;

import java.util.Optional;
import java.util.UUID;

public interface DatabaseOrderRepository {
    public Order save(Order product);

    Optional<Order> findById(Long id);

    public Order delete(Order product);
}
