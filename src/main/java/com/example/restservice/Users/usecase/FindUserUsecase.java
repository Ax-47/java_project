package com.example.restservice.Users.usecase;

import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.User;
import com.example.restservice.Users.dto.FindUserResponseDTO;

import org.springframework.stereotype.Service;

@Service
public class FindUserUsecase {

  private final DatabaseUserRepository databaseUserRepository;

  public FindUserUsecase(DatabaseUserRepository databaseUserRepository) {
    this.databaseUserRepository = databaseUserRepository;
  }

  public FindUserResponseDTO execute(String username) {
    User user = this.databaseUserRepository.findUserByUsername(username);
    return FindUserResponseDTO.from(user);
  }
}
