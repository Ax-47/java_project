package com.example.restservice.Users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Users.domain.PageQuery;
import com.example.restservice.Users.dto.CreateUserRequestDTO;
import com.example.restservice.Users.dto.CreateUserResponseDTO;
import com.example.restservice.Users.dto.FindUserResponseDTO;
import com.example.restservice.Users.dto.PageResponse;
import com.example.restservice.Users.usecase.CreateUserUsecase;
import com.example.restservice.Users.usecase.FindUserUsecase;
import com.example.restservice.Users.usecase.FindUsersUsecase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final CreateUserUsecase createUserUsecase;
  private final FindUserUsecase findUserUsecase;
  private final FindUsersUsecase findAllUsersUsecase;

  public UserController(CreateUserUsecase createUserUsecase
    ,FindUserUsecase findUserUsecase,FindUsersUsecase findAllUsersUsecase) {
    this.createUserUsecase = createUserUsecase;
    this.findUserUsecase = findUserUsecase;
    this.findAllUsersUsecase =findAllUsersUsecase;
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

 @GetMapping
  public ResponseEntity<PageResponse<FindUserResponseDTO>> findAllUsers(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(defaultValue = "username") String sortBy,
          @RequestParam(defaultValue = "true") boolean asc
  ) {

      PageQuery query = new PageQuery(page, size, sortBy, asc);

      return ResponseEntity.ok(
              PageResponse.from(
                      findAllUsersUsecase.execute(query)
              )
      );
  }
}
