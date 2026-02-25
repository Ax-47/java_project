package com.example.restservice.Users.usecase;

import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.HashRepository;
import com.example.restservice.Users.domain.User;
import com.example.restservice.Users.dto.CreateUserRequestDTO;
import com.example.restservice.Users.dto.CreateUserResponseDTO;
import com.example.restservice.Users.exceptions.UsernameAlreadyExistsException;

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
