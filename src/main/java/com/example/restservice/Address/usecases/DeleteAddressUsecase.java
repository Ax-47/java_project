package com.example.restservice.Address.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.Address.domain.*;
import com.example.restservice.Address.dto.*;

@Service
public class DeleteAddressUsecase {

    private final DatabaseAddressRepository databaseAddressRepository;

    public DeleteAddressUsecase(DatabaseAddressRepository databaseAddressRepository) {
        this.databaseAddressRepository = databaseAddressRepository;
    }

    public DeleteAddressResponseDTO execute(DeleteAddressRequestDTO request) {
        Address existingAddress = this.databaseAddressRepository.findById(request.addressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!existingAddress.getUserId().equals(request.userId())) {
            throw new RuntimeException("Unauthorized to delete this address");
        }
        this.databaseAddressRepository.delete(existingAddress);

        return new DeleteAddressResponseDTO("Address was deleted successfully");
    }
}
