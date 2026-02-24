package com.example.restservice.Users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restservice.Users.models.UserModel;

public interface JpaUserRepository
    extends JpaRepository<UserModel, String> {
  boolean existsByUsername(String username);
}
