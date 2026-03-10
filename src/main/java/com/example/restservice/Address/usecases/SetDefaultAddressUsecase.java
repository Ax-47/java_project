package com.example.restservice.Address.usecases;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Address.dto.SetDefaultAddressRequestDTO;
import com.example.restservice.Address.dto.SetDefaultAddressResponseDTO;
import com.example.restservice.Address.exceptions.AddressNotFoundException;
import com.example.restservice.Address.exceptions.UnauthorizedAddressActionException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetDefaultAddressUsecase {

    private final DatabaseAddressRepository repository;

    public SetDefaultAddressUsecase(DatabaseAddressRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public SetDefaultAddressResponseDTO execute(
        SetDefaultAddressRequestDTO request
    ) {
        var addressId = request.addressId();
        var userId = request.userId();

        Address targetAddress = repository
            .findById(addressId)
            .orElseThrow(() ->
                new AddressNotFoundException("Address not found")
            );

        if (!targetAddress.getUserId().equals(userId)) {
            throw new UnauthorizedAddressActionException("Unauthorized");
        }

        repository.clearDefaultByUserId(userId);

        targetAddress.update(
            targetAddress.getFullName(),
            targetAddress.getPhoneNumber().value(),
            targetAddress.getAddressLine1(),
            targetAddress.getAddressLine2(),
            targetAddress.getSubDistrict(),
            targetAddress.getDistrict(),
            targetAddress.getProvince(),
            targetAddress.getPostalCode(),
            targetAddress.getCountry(),
            targetAddress.getLabel(),
            true
        );

        repository.save(targetAddress);
        return new SetDefaultAddressResponseDTO(
            "Address is now set to default"
        );
    }
}
