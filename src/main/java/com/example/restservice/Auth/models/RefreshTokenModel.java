package com.example.restservice.Auth.models;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

import com.example.restservice.Auth.domain.RefreshToken;

@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenModel {
  @Id
  private UUID id;
  @Column(nullable = false)
  private UUID userId;
  @Column(nullable = false)
  private Instant issuedDate;
  @Column(nullable = false)
  private boolean isExpired;

  public RefreshToken toDomain() {
    return RefreshToken.rehydrate(
        id,
        userId,
        issuedDate,
        isExpired);
  }

  public static RefreshTokenModel fromDomain(RefreshToken token) {
    RefreshTokenModel entity = new RefreshTokenModel();
    entity.id = token.getId();
    entity.userId = token.getUserId();
    entity.issuedDate = token.getIssuedDate();
    entity.isExpired = token.isExpired();
    return entity;
  }
}
