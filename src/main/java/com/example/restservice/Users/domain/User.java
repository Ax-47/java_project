package com.example.restservice.Users.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.restservice.Users.exeptions.InvalidPasswordException;
import com.example.restservice.Users.exeptions.InvalidUserNameException;

public class User {
  private final UUID id;
  private final String name;
  private String password;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static User rehydrate(
      UUID id,
      String name,
      String password,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {

    return new User(id, name, password, createdAt, updatedAt);
  }

  User(UUID id, String name, String password,
      LocalDateTime createdAt, LocalDateTime updatedAt) {

    if (name == null || name.isBlank()) {
      throw new InvalidUserNameException("Name cannot be empty");
    }

    if (password == null || password.length() < 8) {
      throw new InvalidPasswordException("Password too short");
    }

    this.id = id;
    this.name = name;
    this.password = password;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static User create(String name, String hashedPassword) {
    return new User(
        UUID.randomUUID(),
        name,
        hashedPassword,
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

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
