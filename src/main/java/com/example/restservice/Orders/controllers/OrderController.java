package com.example.restservice.Orders.controllers;

import com.example.restservice.Orders.dto.CreateOrderRequestDTO;
import com.example.restservice.Orders.dto.CreateOrderResponseDTO;
import com.example.restservice.Orders.dto.CancelOrderRequestDTO;
import com.example.restservice.Orders.dto.CancelOrderResponseDTO;
import com.example.restservice.Orders.usecases.CreateOrderUsecase;
import com.example.restservice.Orders.usecases.CancelOrderUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUsecase createOrderUsecase;
    private final CancelOrderUsecase cancelOrderUsecase;

    public OrderController(CreateOrderUsecase createOrderUsecase, CancelOrderUsecase cancelOrderUsecase) {
        this.createOrderUsecase = createOrderUsecase;
        this.cancelOrderUsecase = cancelOrderUsecase;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponseDTO> createOrder(
            @Valid @RequestBody CreateOrderRequestDTO request,
            Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        CreateOrderResponseDTO response = createOrderUsecase.execute(request, userId);
        return ResponseEntity.created(URI.create("/api/orders")).body(response);
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<CancelOrderResponseDTO> cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        CancelOrderRequestDTO request = new CancelOrderRequestDTO(orderId, userId);
        CancelOrderResponseDTO response = cancelOrderUsecase.execute(request);
        return ResponseEntity.ok(response);
    }
}
