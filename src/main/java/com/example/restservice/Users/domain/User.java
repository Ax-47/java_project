package com.example.restservice.Users.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.example.restservice.Users.exeptions.InvalidPasswordException;
import com.example.restservice.Users.exeptions.InvalidUserNameException;

public class User {
  private final UUID id;
  private final String username;
  private String password;
  private Credit credit;
  private final boolean isAdmin;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static User rehydrate(
      UUID id,
      String username,
      String password,
      BigDecimal credit,
      boolean isAdmin,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {

    return new User(
        id,
        username,
        password,
        Credit.of(credit),
        isAdmin,
        createdAt,
        updatedAt);
  }

  private User(
      UUID id,
      String username,
      String password,
      Credit credit,
      boolean isAdmin,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {

    if (username == null || username.isBlank()) {
      throw new InvalidUserNameException("Name cannot be empty");
    }

    if (password == null || password.length() < 8) {
      throw new InvalidPasswordException("Password too short");
    }

    this.id = Objects.requireNonNull(id);
    this.username = username;
    this.password = password;
    this.credit = Objects.requireNonNull(credit);
    this.isAdmin = isAdmin;
    this.createdAt = Objects.requireNonNull(createdAt);
    this.updatedAt = Objects.requireNonNull(updatedAt);
  }

  public static User create(String name, String hashedPassword) {
    return new User(
        UUID.randomUUID(),
        name,
        hashedPassword,
        Credit.zero(),
        false,
        LocalDateTime.now(),
        LocalDateTime.now());
  }

  public void changePassword(String newHashedPassword) {
    if (newHashedPassword == null || newHashedPassword.length() < 8) {
      throw new InvalidPasswordException("Password too short");
    }
    this.password = newHashedPassword;
    this.updatedAt = LocalDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public Credit getCredit() {
    return this.credit;
  }

  public boolean isAdmin() {
    return this.isAdmin;
  }
}
