package com.example.restservice.Users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.restservice.Users.dto.CreateUserRequestDTO;
import com.example.restservice.Users.dto.CreateUserResponseDTO;
import com.example.restservice.Users.dto.FindUserResponseDTO;
import com.example.restservice.Users.usecase.CreateUserUsecase;
import com.example.restservice.Users.usecase.FindUserUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final CreateUserUsecase createUserUsecase;
  private final FindUserUsecase findUserUsecase;

  public UserController(CreateUserUsecase createUserUsecase,FindUserUsecase findUserUsecase) {
    this.createUserUsecase = createUserUsecase;
    this.findUserUsecase = findUserUsecase;
  }

  @PostMapping
  public ResponseEntity<CreateUserResponseDTO> create(
      @Valid @RequestBody CreateUserRequestDTO requestModel) {

    CreateUserResponseDTO response = createUserUsecase.execute(requestModel);

    return ResponseEntity.ok(response);
  }

 @GetMapping("/{username}")
  public ResponseEntity<FindUserResponseDTO> findByUsername(
      @PathVariable String username) {
    FindUserResponseDTO response =
        findUserUsecase.execute(username);
    return ResponseEntity.ok(response);
  }
}
