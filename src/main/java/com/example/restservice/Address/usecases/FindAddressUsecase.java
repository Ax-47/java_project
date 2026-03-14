package com.example.restservice.Address.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Address.dto.FindAddressRequestDTO;
import com.example.restservice.Address.dto.FindAddressResponseDTO;
import com.example.restservice.Address.exceptions.AddressNotFoundException;

@Service
public class FindAddressUsecase {

  private final DatabaseAddressRepository databaseAddressRepository;

  public FindAddressUsecase(DatabaseAddressRepository databaseAddressRepository) {
    this.databaseAddressRepository = databaseAddressRepository;
  }

  @Transactional(readOnly = true)
  public FindAddressResponseDTO execute(FindAddressRequestDTO request) {

    UUID addressId = request.addressId();

    Address address =
        this.databaseAddressRepository
            .findById(addressId)
            .orElseThrow(
                () -> new AddressNotFoundException("Address not found with ID: " + addressId));

    return new FindAddressResponseDTO(
        address.getId(),
        address.getUserId(),
        address.getFullName(),
        address.getPhoneNumber().value(),
        address.getAddressLine1(),
        address.getAddressLine2(),
        address.getSubDistrict(),
        address.getDistrict(),
        address.getProvince(),
        address.getPostalCode(),
        address.getCountry(),
        address.getLabel(),
        address.isDefault());
  }
}
