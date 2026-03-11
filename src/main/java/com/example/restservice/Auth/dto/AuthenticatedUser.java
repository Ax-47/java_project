package com.example.restservice.Auth.dto;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record AuthenticatedUser(
    UUID userId,
    String username,
    String password,
    Boolean isAdmin) implements UserDetails {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return isAdmin
        ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        : List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

}
