package com.example.restservice.Auth.domain;

import java.util.Optional;
import java.util.UUID;

public interface DatabaseRefreshTokenRepository {
  public RefreshToken save(RefreshToken refreshToken);

  public Optional<RefreshToken> findByTokenId(UUID tokenId);

  public void revokeRefreshToken(UUID tokenId, UUID userId);

  public void revokeRefreshToken(UUID tokenId);
}
