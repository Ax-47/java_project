package com.example.restservice.Auth.domain;

import java.time.Instant;
import java.util.UUID;

import com.example.restservice.Users.domain.User;

public interface TokenRepository {

  public String issueAccessToken(User user, Instant issueDate);

  public String issueRefreshToken(User user, UUID tokenId, String secret, Instant issueDate);

  public DecodedToken decode(String token);
}
