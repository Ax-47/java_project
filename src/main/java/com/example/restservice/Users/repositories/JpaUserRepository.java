package com.example.restservice.Users.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restservice.Users.models.UserModel;

public interface JpaUserRepository
    extends JpaRepository<UserModel, String> {
  boolean existsByUsername(String username);

  Optional<UserModel> findByUsername(String username);
}
