package com.example.restservice.Orders.exceptions;

import java.util.UUID;

public class OrderProductNotFoundException extends RuntimeException {

    public OrderProductNotFoundException(UUID productId) {
        super("Product not found: " + productId);
    }
}
