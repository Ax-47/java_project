package com.example.restservice.Users.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.User;
import com.example.restservice.Users.dto.FindUserResponseDTO;

@Service
public class FindUserByIdUsecase {

  private final DatabaseUserRepository databaseUserRepository;

  public FindUserByIdUsecase(DatabaseUserRepository databaseUserRepository) {
    this.databaseUserRepository = databaseUserRepository;
  }

  public FindUserResponseDTO execute(UUID id) {
    User user = this.databaseUserRepository.findUserByUserId(id).orElseThrow();
    return FindUserResponseDTO.from(user);
  }
}
