package com.example.restservice.Auth.usecases;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Address.exceptions.UnauthorizedAddressActionException;
import com.example.restservice.Auth.domain.DatabaseRefreshTokenRepository;
import com.example.restservice.Auth.domain.RefreshToken;
import com.example.restservice.Auth.domain.TokenRepository;
import com.example.restservice.Auth.dto.SignInRequestDTO;
import com.example.restservice.Auth.dto.TokenResponseDTO;
import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.HashRepository;
import com.example.restservice.Users.domain.User;

@Service
public class SignInUsecase {

  private final DatabaseRefreshTokenRepository databaseRefreshTokenRepository;
  private final DatabaseUserRepository databaseUserRepository;
  private final HashRepository hashRepository;
  private final TokenRepository tokenRepository;

  public SignInUsecase(
      DatabaseRefreshTokenRepository databaseRefreshTokenRepository,
      DatabaseUserRepository databaseUserRepository,
      HashRepository hashRepository,
      TokenRepository tokenRepository) {
    this.databaseRefreshTokenRepository = databaseRefreshTokenRepository;
    this.databaseUserRepository = databaseUserRepository;
    this.hashRepository = hashRepository;
    this.tokenRepository = tokenRepository;
  }

  @Transactional
  public TokenResponseDTO execute(SignInRequestDTO request) {

    String username = request.name();
    User user = databaseUserRepository.findUserByUsername(username);
    if (!hashRepository.matches(request.password(), user.getPassword())) {
      throw new UnauthorizedAddressActionException("Unauthorized");
    }
    var now = Instant.now();
    String secret = UUID.randomUUID().toString();
    String accessToken = tokenRepository.issueAccessToken(user, now);
    String hashedSecret = hashRepository.hash(secret);
    RefreshToken rft =
        databaseRefreshTokenRepository.save(RefreshToken.create(user.getId(), hashedSecret));
    String refreshToken = tokenRepository.issueRefreshToken(user, rft.getId(), secret, now);
    return new TokenResponseDTO(accessToken, refreshToken);
  }
}
