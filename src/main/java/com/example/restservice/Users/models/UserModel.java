package com.example.restservice.Users.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.util.UUID;

import com.example.restservice.Users.domain.User;

@Entity
@Table(name = "users")
public class UserModel {

  @Id
  private UUID id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  protected UserModel() {
  }

  public UserModel(String name, String password) {
    this.name = name;
    this.password = password;
  }

  public static UserModel fromDomain(User user) {
    UserModel model = new UserModel();

    model.id = user.getId();
    model.name = user.getName();
    model.password = user.getPassword();
    model.createdAt = user.getCreatedAt();
    model.updatedAt = user.getUpdatedAt();

    return model;
  }

  public User toDomain() {
    return User.rehydrate(
        id,
        name,
        password,
        createdAt,
        updatedAt);
  }

  @PrePersist
  public void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void onUpdate() {
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
