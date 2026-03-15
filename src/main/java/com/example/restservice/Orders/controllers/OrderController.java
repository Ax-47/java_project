package com.example.restservice.Orders.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Orders.dto.*;
import com.example.restservice.Orders.usecases.CancelOrderUsecase;
import com.example.restservice.Orders.usecases.CompleteOrderUsecase;
import com.example.restservice.Orders.usecases.CreateOrderUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final CreateOrderUsecase createOrderUsecase;
  private final CancelOrderUsecase cancelOrderUsecase;
  private final CompleteOrderUsecase completeOrderUsecase;

  public OrderController(
      CreateOrderUsecase createOrderUsecase,
      CancelOrderUsecase cancelOrderUsecase,
      CompleteOrderUsecase completeOrderUsecase) {
    this.createOrderUsecase = createOrderUsecase;
    this.cancelOrderUsecase = cancelOrderUsecase;
    this.completeOrderUsecase = completeOrderUsecase;
  }

  // POST /api/orders
  @PostMapping("/orders")
  public ResponseEntity<CreateOrderResponseDTO> createOrder(
      @RequestBody @Valid CreateOrderRequestDTO request) {

    UUID mockUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    CreateOrderResponseDTO response = createOrderUsecase.execute(request, mockUserId);

    return ResponseEntity.ok(response);
  }

  // Patch /api/orders
  @PatchMapping("/{orderId}/cancel")
  public ResponseEntity<CancelOrderResponseDTO> cancelOrder(
      @PathVariable UUID orderId, Authentication authentication) {
    UUID userId = UUID.fromString(authentication.getName());
    CancelOrderRequestDTO request = new CancelOrderRequestDTO(orderId, userId);
    CancelOrderResponseDTO response = cancelOrderUsecase.execute(request);
    return ResponseEntity.ok(response);
  }

  // TestTest
  // GET /api/orders
  // @GetMapping
  // public ResponseEntity<PageResponse<CreateOrderResponseDTO>> findAllUsers(
  // @RequestParam(defaultValue = "0") int page,
  // @RequestParam(defaultValue = "10") int size,
  // @RequestParam(defaultValue = "categoryName") String sortBy,
  // @RequestParam(defaultValue = "true") boolean asc) {

  // PageQuery query = new PageQuery(page, size, sortBy, asc);
  // return ResponseEntity.ok(PageResponse.from(findOdersUsecase.execute(query)));
  // }

  // GET /api/orders/{orderId}
  // @GetMapping("/{orderId}")
  // public ResponseEntity<CreateOrderResponseDTO> findById(@PathVariable UUID id)
  // {

  // return ResponseEntity.ok(getOrdersUsecase.execute(id));
  // }
  // PATCH /api/orders/{orderId}/complete
  @PatchMapping("/{orderId}/complete")
  public ResponseEntity<CompleteOrderResponseDTO> create(
      @Valid @RequestBody CompleteOrderRequestDTO request) {
    return ResponseEntity.ok(completeOrderUsecase.execute(request));
  }

  // GET /api/users/{userId}/orders
}
