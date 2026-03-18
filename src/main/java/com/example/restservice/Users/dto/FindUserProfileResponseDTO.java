package com.example.restservice.Users.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FindUserProfileResponseDTO(
    UUID id,
    String username,
    BigDecimal credit,
    LocalDateTime createAt,
    String profileUrl,
    String backgroundUrl) {}
