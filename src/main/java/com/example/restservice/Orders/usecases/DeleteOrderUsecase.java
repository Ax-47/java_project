package com.example.restservice.Orders.usecases;

import com.example.restservice.Orders.domain.*;
import com.example.restservice.Orders.dto.*;
import org.springframework.stereotype.Service;

@Service
public class DeleteOrderUsecase {

    private final DatabaseOrderRepository databaseOrderRepository;

    public DeleteOrderUsecase(DatabaseOrderRepository databaseOrderRepository) {
        this.databaseOrderRepository = databaseOrderRepository;
    }

    public DeleteOrderResponseDTO execute(DeleteOrderRequestDTO request) {
        Order existingOrder = this.databaseOrderRepository.findById(
            request.orderId()
        ).orElseThrow(() -> new RuntimeException("Order not found"));

        if (!existingOrder.getUserId().equals(request.userId())) {
            throw new RuntimeException("Unauthorized to cancel this order");
        }

        this.databaseOrderRepository.delete(existingOrder);

        return new DeleteOrderResponseDTO("Order was cancelled successfully");
    }
}
