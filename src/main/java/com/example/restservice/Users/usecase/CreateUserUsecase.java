package com.example.restservice.Users.usecase;

import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.HashRepository;
import com.example.restservice.Users.domain.User;
import com.example.restservice.Users.dto.CreateUserRequestDTO;
import com.example.restservice.Users.dto.CreateUserResponseDTO;

import org.springframework.stereotype.Service;

@Service
public class CreateUserUsecase {

  private final DatabaseUserRepository databaseUserRepository;
  private final HashRepository hashRepository;

  public CreateUserUsecase(DatabaseUserRepository databaseUserRepository, HashRepository hashRepository) {
    this.databaseUserRepository = databaseUserRepository;
    this.hashRepository = hashRepository;
  }

  public CreateUserResponseDTO execute(CreateUserRequestDTO request) {

    if (databaseUserRepository.existsByUsername(request.name())) {
      return new CreateUserResponseDTO("username has been used");
    }
    String hashedPassword = hashRepository.hash(request.password());
    User user = User.create(request.name(), hashedPassword);
    databaseUserRepository.save(user);
    return new CreateUserResponseDTO("User has been created");
  }
}
