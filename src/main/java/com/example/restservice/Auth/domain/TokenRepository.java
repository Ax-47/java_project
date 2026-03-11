package com.example.restservice.Auth.domain;

import java.time.Instant;

import com.example.restservice.Users.domain.User;

public interface TokenRepository {

  public String issueAccessToken(User user, Instant issueDate);

  public String issueRefreshToken(User user, Instant issueDate);
}
