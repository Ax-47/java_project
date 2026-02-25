package com.example.restservice.Users.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.restservice.Users.domain.User;

public record FindUserResponseDTO(
    UUID id,
    String username,
    BigDecimal credit,
    boolean isAdmin,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
  public static FindUserResponseDTO from(User user) {
    return new FindUserResponseDTO(
        user.getId(),
        user.getUsername(),
        user.getCredit().getValue(),
        user.isAdmin(),
        user.getCreatedAt(),
        user.getUpdatedAt());
  }
}
