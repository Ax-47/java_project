package com.example.restservice.Auth.domain;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken {

  private final UUID id;
  private final UUID userId;
  private final Instant issuedDate;
  private boolean isExpired;

  private RefreshToken(
      UUID id,
      UUID userId,
      Instant issuedDate,
      boolean isExpired) {
    this.id = id;
    this.userId = userId;
    this.issuedDate = issuedDate;
    this.isExpired = isExpired;
  }

  public static RefreshToken create(UUID userId) {
    return new RefreshToken(
        UUID.randomUUID(),
        userId,
        Instant.now(),
        false);
  }

  public static RefreshToken rehydrate(
      UUID id,
      UUID userId,
      Instant issuedDate,
      boolean isExpired) {
    return new RefreshToken(
        id,
        userId,
        issuedDate,
        isExpired);
  }

  public void expire() {
    this.isExpired = true;
  }

  public boolean isExpired() {
    return isExpired;
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public Instant getIssuedDate() {
    return issuedDate;
  }
}
