package com.example.restservice.Users.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.dto.UploadImageResponseDTO;
import com.example.restservice.Images.usecases.UploadUserImageUsecase;
import com.example.restservice.Users.domain.PageQuery;
import com.example.restservice.Users.dto.*;
import com.example.restservice.Users.usecases.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final CreateUserUsecase createUserUsecase;
  private final FindUserUsecase findUserUsecase;
  private final FindUsersUsecase findAllUsersUsecase;
  private final UploadUserImageUsecase uploadUserImageUsecase;

  public UserController(
      CreateUserUsecase createUserUsecase,
      FindUserUsecase findUserUsecase,
      UploadUserImageUsecase uploadUserImageUsecase,
      FindUsersUsecase findAllUsersUsecase) {
    this.createUserUsecase = createUserUsecase;
    this.findUserUsecase = findUserUsecase;
    this.findAllUsersUsecase = findAllUsersUsecase;
    this.uploadUserImageUsecase = uploadUserImageUsecase;
  }

  @PostMapping
  public ResponseEntity<CreateUserResponseDTO> create(
      @Valid @RequestBody CreateUserRequestDTO requestModel) {

    CreateUserResponseDTO response = createUserUsecase.execute(requestModel);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{username}")
  public ResponseEntity<FindUserResponseDTO> findByUsername(@PathVariable String username) {
    FindUserResponseDTO response = findUserUsecase.execute(username);
    return ResponseEntity.ok(response);
  }

  // NOTE: ADMIN only
  @GetMapping
  public ResponseEntity<PageResponse<FindUserResponseDTO>> findAllUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "username") String sortBy,
      @RequestParam(defaultValue = "true") boolean asc) {

    PageQuery query = new PageQuery(page, size, sortBy, asc);

    return ResponseEntity.ok(PageResponse.from(findAllUsersUsecase.execute(query)));
  }

  @PostMapping("/{userId}/profile")
  public ResponseEntity<UploadImageResponseDTO> uploadProfile(
      @PathVariable UUID userId, @RequestParam MultipartFile file) throws IOException {
    UploadImageResponseDTO response =
        uploadUserImageUsecase.execute(file, userId, ImageResourceType.USER_PROFILE);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{userId}/backgound")
  public ResponseEntity<UploadImageResponseDTO> uploadBackgound(
      @PathVariable UUID userId, @RequestParam MultipartFile file) throws IOException {

    UploadImageResponseDTO response =
        uploadUserImageUsecase.execute(file, userId, ImageResourceType.USER_BACKGROUND);

    return ResponseEntity.ok(response);
  }
  // GET /api/users
  // GET /api/users/{userId}
  // PUT /api/users/{userId}
  // DELETE /api/users/{userId}
}
