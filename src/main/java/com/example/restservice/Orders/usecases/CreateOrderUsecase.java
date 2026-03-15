package com.example.restservice.Orders.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Address.exceptions.AddressNotFoundException;
import com.example.restservice.Orders.domain.*;
import com.example.restservice.Orders.dto.*;
import com.example.restservice.Orders.exceptions.OrderProductNotFoundException;
import com.example.restservice.Products.domain.*;

@Service
public class CreateOrderUsecase {

  private final DatabaseOrderRepository orderRepository;
  private final DatabaseProductRepository productRepository;
  private final DatabaseAddressRepository databaseAddressRepository;

  public CreateOrderUsecase(
      DatabaseOrderRepository orderRepository,
      DatabaseProductRepository productRepository,
      DatabaseAddressRepository databaseAddressRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
    this.databaseAddressRepository = databaseAddressRepository;
  }

  @Transactional
  public CreateOrderResponseDTO execute(CreateOrderRequestDTO request, UUID userId) {
    Product product =
        productRepository
            .findById(request.productId())
            .orElseThrow(() -> new OrderProductNotFoundException(request.productId()));

    ProductSnapshot snapshot =
        new ProductSnapshot(product.getId(), product.getName(), product.getPrice());

    Address dbAddress =
        databaseAddressRepository
            .findByUserId(userId)
            .orElseThrow(
                () -> new AddressNotFoundException("Not Found Address by userId: " + userId));

    OrderAddress address =
        new OrderAddress(
            dbAddress.getFullName(),
            dbAddress.getPhoneNumber(),
            dbAddress.getAddressLine1(),
            dbAddress.getAddressLine2(),
            dbAddress.getSubDistrict(),
            dbAddress.getDistrict(),
            dbAddress.getProvince(),
            dbAddress.getPostalCode(),
            dbAddress.getCountry());
    Order order = Order.create(userId, snapshot, address);

    orderRepository.save(order);

    return new CreateOrderResponseDTO("Order created successfully");
  }
}
