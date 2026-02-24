package com.example.restservice.Users.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

import com.example.restservice.Users.domain.User;

@Entity
@Table(name = "users")
public class UserModel {

  @Id
  private UUID id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal credit;

  @Column(nullable = false)
  private boolean isAdmin;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  protected UserModel() {
  }

  public static UserModel fromDomain(User user) {
    UserModel model = new UserModel();

    model.id = user.getId();
    model.username = user.getUsername();
    model.password = user.getPassword();
    model.credit = user.getCredit().getValue();
    model.isAdmin = user.isAdmin();
    model.createdAt = user.getCreatedAt();
    model.updatedAt = user.getUpdatedAt();

    return model;
  }

  public User toDomain() {
    return User.rehydrate(
        id,
        username,
        password,
        credit,
        isAdmin,
        createdAt,
        updatedAt);
  }

  @PrePersist
  public void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();

    if (this.credit == null) {
      this.credit = BigDecimal.ZERO;
    }
  }

  @PreUpdate
  public void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public BigDecimal getCredit() {
    return credit;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
