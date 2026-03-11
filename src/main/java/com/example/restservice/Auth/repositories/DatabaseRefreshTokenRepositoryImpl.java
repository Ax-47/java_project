package com.example.restservice.Auth.repositories;

import org.springframework.stereotype.Repository;
import com.example.restservice.Auth.domain.DatabaseRefreshTokenRepository;
import com.example.restservice.Auth.domain.RefreshToken;
import com.example.restservice.Auth.models.RefreshTokenModel;

@Repository
public class DatabaseRefreshTokenRepositoryImpl implements DatabaseRefreshTokenRepository {
  private final JpaRefreshTokenRepository jpaRefreshTokenRepository;

  public DatabaseRefreshTokenRepositoryImpl(JpaRefreshTokenRepository jpaRefreshTokenRepository) {
    this.jpaRefreshTokenRepository = jpaRefreshTokenRepository;
  }

  @Override
  public RefreshToken save(RefreshToken refreshToken) {
    RefreshTokenModel model = RefreshTokenModel.fromDomain(refreshToken);
    RefreshTokenModel saved = jpaRefreshTokenRepository.save(model);
    return saved.toDomain();
  }

}
