package com.example.restservice.Users.usecase;

import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.HashRepository;
import com.example.restservice.Users.domain.User;
import com.example.restservice.Users.dto.CreateUserResponseDTO;
import com.example.restservice.Users.dto.FindUserRequestDTO;
import com.example.restservice.Users.dto.FindUserResponseDTO;
import com.example.restservice.Users.exceptions.UsernameAlreadyExistsException;

import org.springframework.stereotype.Service;

@Service
public class FindUserUsecase {

  private final DatabaseUserRepository databaseUserRepository;

  public FindUserUsecase(DatabaseUserRepository databaseUserRepository) {
    this.databaseUserRepository = databaseUserRepository;
  }

  public FindUserResponseDTO execute(FindUserRequestDTO request) {
    String username = request.name();
    User user = this.databaseUserRepository.findUserByUsername(username);
    return FindUserResponseDTO.from(user);
  }
}
