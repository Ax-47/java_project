package com.example.restservice.Address.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Address.dto.CreateAddressRequestDTO;
import com.example.restservice.Address.dto.CreateAddressResponseDTO;
import com.example.restservice.Address.usecases.CreateAddressUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

  private final CreateAddressUsecase createAddressUsecase;

  public AddressController(CreateAddressUsecase createAddressUsecase) {
    this.createAddressUsecase = createAddressUsecase;
  }

  @PostMapping
  public ResponseEntity<CreateAddressResponseDTO> create(
      @Valid @RequestBody CreateAddressRequestDTO requestModel) {

    CreateAddressResponseDTO responses = createAddressUsecase.execute(requestModel);

    return ResponseEntity.ok(responses);
  }
}
