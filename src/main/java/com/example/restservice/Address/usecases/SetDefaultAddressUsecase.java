package com.example.restservice.Address.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Address.dto.SetDefaultAddressRequestDTO;
import com.example.restservice.Address.dto.SetDefaultAddressResponseDTO;
import com.example.restservice.Address.exceptions.AddressNotFoundException;
import com.example.restservice.Address.exceptions.UnauthorizedAddressActionException;

@Service
public class SetDefaultAddressUsecase {

  private final DatabaseAddressRepository repository;

  public SetDefaultAddressUsecase(DatabaseAddressRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public SetDefaultAddressResponseDTO execute(SetDefaultAddressRequestDTO request) {
    var addressId = request.addressId();
    var userId = request.userId();

    Address targetAddress =
        repository
            .findById(addressId)
            .orElseThrow(() -> new AddressNotFoundException("Address not found"));

    if (!targetAddress.getUserId().equals(userId)) {
      throw new UnauthorizedAddressActionException("Unauthorized");
    }
    repository.clearDefaultByUserId(userId);
    repository.setDefaultAddress(addressId, userId);
    repository.save(targetAddress);
    return new SetDefaultAddressResponseDTO("Address is now set to default");
  }
}
