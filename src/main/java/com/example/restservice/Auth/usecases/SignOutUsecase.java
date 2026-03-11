package com.example.restservice.Auth.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Auth.domain.DatabaseRefreshTokenRepository;
import com.example.restservice.Auth.domain.DecodedToken;
import com.example.restservice.Auth.domain.RefreshToken;
import com.example.restservice.Auth.domain.TokenRepository;
import com.example.restservice.Exeptions.UnauthorizedException;

@Service
public class SignOutUsecase {

  private final TokenRepository tokenRepository;
  private final DatabaseRefreshTokenRepository refreshTokenRepository;

  public SignOutUsecase(
      TokenRepository tokenRepository,
      DatabaseRefreshTokenRepository refreshTokenRepository) {
    this.tokenRepository = tokenRepository;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  @Transactional
  public void execute(String refreshToken) {

    DecodedToken jwt = tokenRepository.decode(refreshToken);

    RefreshToken token = refreshTokenRepository
        .findByTokenId(jwt.tokenId())
        .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

    refreshTokenRepository.revokeRefreshToken(token.getId());
  }
}
