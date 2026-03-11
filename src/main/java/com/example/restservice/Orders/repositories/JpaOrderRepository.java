package com.example.restservice.Orders.repositories;

import com.example.restservice.Orders.models.OrderModel;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderModel, UUID> {
}
