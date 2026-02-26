package com.example.restservice.Users.usecases;

import com.example.restservice.Users.domain.*;
import com.example.restservice.Users.dto.*;
import com.example.restservice.Users.exceptions.*;

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

    String username = request.name();
    String password = request.password();
    if (databaseUserRepository.existsByUsername(username)) {
      throw new UsernameAlreadyExistsException(username);
    }
    String hashedPassword = hashRepository.hash(password);
    User user = User.create(username, hashedPassword);
    databaseUserRepository.save(user);
    return new CreateUserResponseDTO("User has been created");
  }
}
