package com.example.restservice.Address.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Address.dto.UpdateAddressRequestDTO;
import com.example.restservice.Address.dto.UpdateAddressResponseDTO;
import com.example.restservice.Address.exceptions.AddressNotFoundException;

@Service
public class UpdateAddressUsecase {

  private final DatabaseAddressRepository databaseAddressRepository;

  public UpdateAddressUsecase(DatabaseAddressRepository databaseAddressRepository) {
    this.databaseAddressRepository = databaseAddressRepository;
  }

  @Transactional
  public UpdateAddressResponseDTO execute(UpdateAddressRequestDTO request) {

    Address address =
        this.databaseAddressRepository
            .findById(request.addressId())
            .orElseThrow(
                () ->
                    new AddressNotFoundException(
                        "Address not found with ID: " + request.addressId()));

    boolean defaultStatus = request.isDefault() != null ? request.isDefault() : address.isDefault();
    if (defaultStatus && !address.isDefault()) {
      this.databaseAddressRepository.clearDefaultByUserId(address.getUserId());
    }
    address.update(
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

    this.databaseAddressRepository.save(address);

    return new UpdateAddressResponseDTO("Address was updated successfully");
  }
}
