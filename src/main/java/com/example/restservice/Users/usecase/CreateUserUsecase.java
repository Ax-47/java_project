package com.example.restservice.Users.usecase;

import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.User;
import com.example.restservice.Users.dto.CreateUserRequestDTO;
import com.example.restservice.Users.dto.CreateUserResponseDTO;

import org.springframework.stereotype.Service;

@Service
public class CreateUserUsecase {

  private final DatabaseUserRepository databaseUserRepository;

  public CreateUserUsecase(DatabaseUserRepository databaseUserRepository) {
    this.databaseUserRepository = databaseUserRepository;
  }

  public CreateUserResponseDTO execute(CreateUserRequestDTO request) {

    if (databaseUserRepository.existsByUsername(request.name())) {
      return new CreateUserResponseDTO("username has been used");
    }

    User user = User.create(request.name(), request.password());

    databaseUserRepository.save(user);

    return new CreateUserResponseDTO("User has been created");
  }
}
