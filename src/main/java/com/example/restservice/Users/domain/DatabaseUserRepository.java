package com.example.restservice.Users.domain;

import java.util.Optional;

public interface DatabaseUserRepository {
  public User save(User user);

  public boolean existsByUsername(String username);

  public User findUserByUsername(String username);
}
