package com.example.restservice.Users.domain;

import java.util.Optional;
import java.util.UUID;

public interface DatabaseUserRepository {
  public User save(User user);

  public boolean existsByUsername(String username);

  public boolean existsByUserId(UUID userid);

  public User findUserByUsername(String username);

  public Optional<User> findUserByUserId(UUID userId);

  Page<User> findAllUsers(PageQuery pageable);
}
