package com.example.restservice.Address.usecases;

import org.springframework.stereotype.Service;

import com.example.restservice.Address.domain.*;
import com.example.restservice.Address.dto.*;

@Service
public class CreateAddressUsecase {

    private final DatabaseAddressRepository databaseAddressRepository;

    public CreateAddressUsecase(DatabaseAddressRepository databaseAddressRepository) {
        this.databaseAddressRepository = databaseAddressRepository;
    }

    public CreateAddressResponseDTO execute(CreateAddressRequestDTO request) {
        boolean defaultStatus = request.isDefault() != null ? request.isDefault() : false;
        Address newAddress = Address.create(
                request.userId(),
                request.fullName(),
                request.phoneNumber(),
                request.addressLine1(),
                request.addressLine2(),
                request.subDistrict(),
                request.district(),
                request.province(),
                request.postalCode(),
                request.country(),
                request.label(),
                defaultStatus);
        this.databaseAddressRepository.save(newAddress);
        return new CreateAddressResponseDTO("Address was created");
    }
}
