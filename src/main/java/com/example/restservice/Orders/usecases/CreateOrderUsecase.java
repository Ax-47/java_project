package com.example.restservice.Orders.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Address.domain.PhoneNumber;
import com.example.restservice.Orders.domain.*;
import com.example.restservice.Orders.dto.*;
import com.example.restservice.Orders.exceptions.OrderProductNotFoundException;
import com.example.restservice.Products.domain.*;

@Service
public class CreateOrderUsecase {

  private final DatabaseOrderRepository orderRepository;
  private final DatabaseProductRepository productRepository;

  public CreateOrderUsecase(
      DatabaseOrderRepository orderRepository, DatabaseProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  @Transactional
  public CreateOrderResponseDTO execute(CreateOrderRequestDTO request, UUID userId) {
    Product product =
        productRepository
            .findById(request.productId())
            .orElseThrow(() -> new OrderProductNotFoundException(request.productId()));

    ProductSnapshot snapshot =
        new ProductSnapshot(product.getId(), product.getName(), product.getPrice());

    PhoneNumber phoneNumber = PhoneNumber.of(request.phoneNumber());

    OrderAddress address =
        new OrderAddress(
            request.fullName(),
            phoneNumber,
            request.addressLine1(),
            request.addressLine2(),
            request.subDistrict(),
            request.district(),
            request.province(),
            request.postalCode(),
            request.country());

    Order order = Order.create(userId, snapshot, address);

    orderRepository.save(order);

    return new CreateOrderResponseDTO("Order created successfully");
  }
}
