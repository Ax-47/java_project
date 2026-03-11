package com.example.restservice.Auth.domain;

public interface DatabaseRefreshTokenRepository {
  public RefreshToken save(RefreshToken refreshToken);

}
