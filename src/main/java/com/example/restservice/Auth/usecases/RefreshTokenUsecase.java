package com.example.restservice.Auth.usecases;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Auth.domain.DatabaseRefreshTokenRepository;
import com.example.restservice.Auth.domain.DecodedToken;
import com.example.restservice.Auth.domain.RefreshToken;
import com.example.restservice.Auth.domain.TokenRepository;
import com.example.restservice.Auth.dto.TokenResponseDTO;
import com.example.restservice.Exeptions.UnauthorizedException;
import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.HashRepository;
import com.example.restservice.Users.domain.User;

@Service
public class RefreshTokenUsecase {

  private final DatabaseRefreshTokenRepository databaseRefreshTokenRepository;
  private final DatabaseUserRepository databaseUserRepository;
  private final HashRepository hashRepository;
  private final TokenRepository tokenRepository;

  public RefreshTokenUsecase(
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
  public TokenResponseDTO execute(String bearer) {
    DecodedToken jwt = tokenRepository.decode(bearer);
    RefreshToken token =
        databaseRefreshTokenRepository
            .findByTokenId(jwt.tokenId())
            .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));
    if (token.isExpired()
        || !hashRepository.matches(jwt.secret(), token.getToken())
        || !"refresh".equals(jwt.type())) {
      throw new UnauthorizedException("Invalid refresh token");
    }

    User user =
        databaseUserRepository
            .findUserByUserId(token.getUserId())
            .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

    Instant now = Instant.now();
    String accessToken = tokenRepository.issueAccessToken(user, now);
    String secret = UUID.randomUUID().toString();
    String hashedSecret = hashRepository.hash(secret);
    databaseRefreshTokenRepository.revokeRefreshToken(token.getId(), user.getId());
    RefreshToken rft =
        databaseRefreshTokenRepository.save(RefreshToken.create(user.getId(), hashedSecret));
    String refreshToken = tokenRepository.issueRefreshToken(user, rft.getId(), secret, now);
    return new TokenResponseDTO(accessToken, refreshToken);
  }
}
