package com.example.restservice.Users.domain;

import java.time.LocalDateTime;

public interface UserFactory {
  User create(String name, String password);
}
