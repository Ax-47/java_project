package com.example.restservice.Auth.usecases;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.restservice.Auth.dto.AuthenticatedUser;
import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.User;

@Service
public class CustomUserDetailsUsecase implements UserDetailsService {

  private final DatabaseUserRepository databaseUserRepository;

  public CustomUserDetailsUsecase(DatabaseUserRepository databaseUserRepository) {
    this.databaseUserRepository = databaseUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {

    User user = databaseUserRepository.findUserByUsername(username);
    return new AuthenticatedUser(user.getId(), user.getUsername(), user.getPassword(), user.isAdmin());
  }
}
