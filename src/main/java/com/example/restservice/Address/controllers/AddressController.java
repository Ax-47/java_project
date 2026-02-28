package com.example.restservice.Address.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Address.dto.CreateAddressRequestDTO;
import com.example.restservice.Address.dto.CreateAddressResponseDTO;
import com.example.restservice.Address.dto.DeleteAddressRequestDTO;
import com.example.restservice.Address.dto.DeleteAddressResponseDTO;
import com.example.restservice.Address.usecases.CreateAddressUsecase;
import com.example.restservice.Address.usecases.DeleteAddressUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

  private final CreateAddressUsecase createAddressUsecase;
  private final DeleteAddressUsecase deleteAddressUsecase;

  public AddressController(CreateAddressUsecase createAddressUsecase,DeleteAddressUsecase deleteAddressUsecase) {
    this.createAddressUsecase = createAddressUsecase;
    this.deleteAddressUsecase = deleteAddressUsecase;
  }

  @PostMapping
  public ResponseEntity<CreateAddressResponseDTO> create(
      @Valid @RequestBody CreateAddressRequestDTO requestModel) {

    CreateAddressResponseDTO response = createAddressUsecase.execute(requestModel);

    return ResponseEntity.ok(response);
  }
  @DeleteMapping
  public ResponseEntity<DeleteAddressResponseDTO> delete(
    @Valid @RequestBody DeleteAddressRequestDTO requestModel) {

    DeleteAddressResponseDTO response = deleteAddressUsecase.execute(requestModel);

    return ResponseEntity.ok(response);
  }
}
