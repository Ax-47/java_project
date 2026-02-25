package com.example.restservice.Users.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
  public UsernameAlreadyExistsException(String username) {
    super("Username already exists: " + username);
  }
}
