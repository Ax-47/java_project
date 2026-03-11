package com.example.restservice.Orders.usecases;

import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.dto.CancelOrderRequestDTO;
import com.example.restservice.Orders.dto.CancelOrderResponseDTO;
import com.example.restservice.Orders.exceptions.OrderNotFoundException;
import com.example.restservice.Products.exceptions.UnauthorizedProductActionException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelOrderUsecase {

    private final DatabaseOrderRepository databaseOrderRepository;

    public CancelOrderUsecase(DatabaseOrderRepository databaseOrderRepository) {
        this.databaseOrderRepository = databaseOrderRepository;
    }

    @Transactional
    public CancelOrderResponseDTO execute(CancelOrderRequestDTO request) {
        Order existingOrder = this.databaseOrderRepository.findById(
            request.orderId()
        ).orElseThrow(() ->
            new OrderNotFoundException(
                "Order not found with ID: " + request.orderId()
            )
        );

        if (!existingOrder.getUserId().equals(request.userId())) {
            throw new UnauthorizedProductActionException("Unauthorized to cancel this order");
        }

        existingOrder.cancel();

        this.databaseOrderRepository.save(existingOrder);

        return new CancelOrderResponseDTO("Order was cancelled successfully");
    }
}
