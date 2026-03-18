package com.example.restservice.Users.exceptions;

import com.example.restservice.Exeptions.DomainException;

public class UsernameAlreadyExistsException extends DomainException {
  public UsernameAlreadyExistsException(String username) {
    super("Username already exists: " + username);
  }
}
