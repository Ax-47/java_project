package com.example.restservice.Auth.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Address.exceptions.UnauthorizedAddressActionException;
import com.example.restservice.Auth.domain.DatabaseRefreshTokenRepository;
import com.example.restservice.Auth.domain.RefreshToken;
import com.example.restservice.Auth.dto.SignInRequestDTO;
import com.example.restservice.Auth.dto.SignInResponseDTO;
import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.HashRepository;
import com.example.restservice.Users.domain.User;

@Service
public class SignInUsecase {

  private final DatabaseRefreshTokenRepository databaseRefreshTokenRepository;
  private final DatabaseUserRepository databaseUserRepository;
  private final HashRepository hashRepository;

  public SignInUsecase(DatabaseRefreshTokenRepository databaseRefreshTokenRepository,
      DatabaseUserRepository databaseUserRepository, HashRepository hashRepository) {
    this.databaseRefreshTokenRepository = databaseRefreshTokenRepository;
    this.databaseUserRepository = databaseUserRepository;
    this.hashRepository = hashRepository;
  }

  @Transactional
  public SignInResponseDTO execute(SignInRequestDTO request) {

    String username = request.name();
    User user = databaseUserRepository.findUserByUsername(username);
    if (!hashRepository.matches(request.password(), user.getPassword())) {
      throw new UnauthorizedAddressActionException("Unauthorized");
    }
    RefreshToken savedRefreshToken = databaseRefreshTokenRepository.save(RefreshToken.create(user.getId()));
    // jwt token
    return new SignInResponseDTO("ac token", savedRefreshToken.getId().toString());
  }
}
