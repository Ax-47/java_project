package com.example.restservice.Users.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface DatabaseUserRepository {
  public User save(User user);

  public boolean existsByUsername(String username);

  public boolean existsByUserId(UUID userid);

  public User findUserByUsername(String username);

  public List<User> findAllByUserIds(Set<UUID> userIds);

  public Optional<User> findUserByUserId(UUID userId);

  Page<User> findAllUsers(PageQuery pageable);
}
