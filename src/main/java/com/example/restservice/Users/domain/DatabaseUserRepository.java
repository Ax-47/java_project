package com.example.restservice.Users.domain;

public interface DatabaseUserRepository {
  public User save(User user);

  public boolean existsByUsername(String username);

}
