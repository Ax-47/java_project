package com.example.restservice.Address.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.example.restservice.Address.models.AddressSortField;
import com.example.restservice.Address.usecases.CreateAddressUsecase;
import com.example.restservice.Address.usecases.DeleteAddressUsecase;
import com.example.restservice.Address.usecases.FindAddressUsecase;
import com.example.restservice.Address.usecases.FindAddressesByUserIdUsecase;
import com.example.restservice.Address.usecases.FindAddressesUsecase;
import com.example.restservice.Address.usecases.SetDefaultAddressUsecase;
import com.example.restservice.Address.usecases.UpdateAddressUsecase;
import com.example.restservice.Auth.dto.UserPrincipalDTO;
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
  private final FindAddressesByUserIdUsecase findAddressesByUserIdUsecase;

  public AddressController(
      CreateAddressUsecase createAddressUsecase,
      DeleteAddressUsecase deleteAddressUsecase,
      SetDefaultAddressUsecase setDefaultAddressUsecase,
      FindAddressUsecase findAddressUsecase,
      FindAddressesUsecase findAddressesUsecase,
      FindAddressesByUserIdUsecase findAddressesByUserIdUsecase,
      UpdateAddressUsecase updateAddressUsecase) {
    this.createAddressUsecase = createAddressUsecase;
    this.deleteAddressUsecase = deleteAddressUsecase;
    this.setDefaultAddressUsecase = setDefaultAddressUsecase;
    this.findAddressUsecase = findAddressUsecase;
    this.findAddressesUsecase = findAddressesUsecase;
    this.updateAddressUsecase = updateAddressUsecase;
    this.findAddressesByUserIdUsecase = findAddressesByUserIdUsecase;
  }

  @PostMapping
  public ResponseEntity<CreateAddressResponseDTO> create(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @Valid @RequestBody CreateAddressRequestDTO requestModel) {

    CreateAddressResponseDTO response = createAddressUsecase.execute(user.id(), requestModel);

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
  public ResponseEntity<Page<FindAddressResponseDTO>> findAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "updatedAt") String sortBy,
      @RequestParam(defaultValue = "true") boolean asc) {
    AddressSortField sortField = AddressSortField.fromString(sortBy);
    PageQuery query = new PageQuery(page, size, sortField.getFieldName(), asc);
    Page<FindAddressResponseDTO> response = this.findAddressesUsecase.execute(query);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/user")
  public ResponseEntity<Page<FindAddressResponseDTO>> findAllByUserId(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "updatedAt") String sortBy,
      @RequestParam(defaultValue = "false") boolean asc) {
    AddressSortField sortField = AddressSortField.fromString(sortBy);
    PageQuery query = new PageQuery(page, size, sortField.getFieldName(), asc);
    Page<FindAddressResponseDTO> response =
        this.findAddressesByUserIdUsecase.execute(user.id(), query);
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
