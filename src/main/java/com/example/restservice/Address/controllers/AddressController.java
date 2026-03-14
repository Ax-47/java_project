package com.example.restservice.Address.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Address.dto.CreateAddressRequestDTO;
import com.example.restservice.Address.dto.CreateAddressResponseDTO;
import com.example.restservice.Address.dto.DeleteAddressRequestDTO;
import com.example.restservice.Address.dto.DeleteAddressResponseDTO;
import com.example.restservice.Address.dto.FindAddressRequestDTO;
import com.example.restservice.Address.dto.FindAddressResponseDTO;
import com.example.restservice.Address.dto.SetDefaultAddressRequestDTO;
import com.example.restservice.Address.dto.SetDefaultAddressResponseDTO;
import com.example.restservice.Address.dto.UpdateAddressRequestDTO;
import com.example.restservice.Address.dto.UpdateAddressResponseDTO;
import com.example.restservice.Address.usecases.CreateAddressUsecase;
import com.example.restservice.Address.usecases.DeleteAddressUsecase;
import com.example.restservice.Address.usecases.FindAddressUsecase;
import com.example.restservice.Address.usecases.FindAddressesUsecase;
import com.example.restservice.Address.usecases.SetDefaultAddressUsecase;
import com.example.restservice.Address.usecases.UpdateAddressUsecase;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

  private final CreateAddressUsecase createAddressUsecase;
  private final DeleteAddressUsecase deleteAddressUsecase;
  private final SetDefaultAddressUsecase setDefaultAddressUsecase;
  private final FindAddressUsecase findAddressUsecase;
  private final FindAddressesUsecase findAddressesUsecase;
  private final UpdateAddressUsecase updateAddressUsecase;

  public AddressController(
      CreateAddressUsecase createAddressUsecase,
      DeleteAddressUsecase deleteAddressUsecase,
      SetDefaultAddressUsecase setDefaultAddressUsecase,
      FindAddressUsecase findAddressUsecase,
      FindAddressesUsecase findAddressesUsecase,
      UpdateAddressUsecase updateAddressUsecase) {
    this.createAddressUsecase = createAddressUsecase;
    this.deleteAddressUsecase = deleteAddressUsecase;
    this.setDefaultAddressUsecase = setDefaultAddressUsecase;
    this.findAddressUsecase = findAddressUsecase;
    this.findAddressesUsecase = findAddressesUsecase;
    this.updateAddressUsecase = updateAddressUsecase;
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

  @PatchMapping("/default")
  public ResponseEntity<SetDefaultAddressResponseDTO> setDefault(
      @Valid @RequestBody SetDefaultAddressRequestDTO requestModel) {

    SetDefaultAddressResponseDTO response = this.setDefaultAddressUsecase.execute(requestModel);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<Page<FindAddressResponseDTO>> findAll(PageQuery query) {

    Page<FindAddressResponseDTO> response = this.findAddressesUsecase.execute(query);

    return ResponseEntity.ok(response);
  }

  @PutMapping
  public ResponseEntity<UpdateAddressResponseDTO> update(
      @Valid @RequestBody UpdateAddressRequestDTO requestModel) {

    UpdateAddressResponseDTO response = this.updateAddressUsecase.execute(requestModel);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{addressId}")
  public ResponseEntity<FindAddressResponseDTO> findById(@PathVariable UUID addressId) {

    FindAddressRequestDTO requestModel = new FindAddressRequestDTO(addressId);
    FindAddressResponseDTO response = this.findAddressUsecase.execute(requestModel);

    return ResponseEntity.ok(response);
  }
}
