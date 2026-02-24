package com.example.restservice.Users.domain;

public interface HashRepository {
  public String hash(String raw);

  public boolean matches(String raw, String hashed);
}
