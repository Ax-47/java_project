package com.example.restservice.Users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.restservice.Users.dto.CreateUserRequestDTO;
import com.example.restservice.Users.dto.CreateUserResponseDTO;
import com.example.restservice.Users.usecase.CreateUserUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final CreateUserUsecase createUserUsecase;

  public UserController(CreateUserUsecase createUserUsecase) {
    this.createUserUsecase = createUserUsecase;
  }

  @PostMapping
  public ResponseEntity<CreateUserResponseDTO> create(
      @Valid @RequestBody CreateUserRequestDTO requestModel) {

    CreateUserResponseDTO response = createUserUsecase.execute(requestModel);

    return ResponseEntity.ok(response);
  }
}
