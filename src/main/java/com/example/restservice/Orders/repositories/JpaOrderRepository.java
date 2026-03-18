package com.example.restservice.Orders.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restservice.Orders.models.OrderModel;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderModel, UUID> {

  Page<OrderModel> findAllByUserId(UUID userId, Pageable pageable);
}
