package com.example.restservice.Users.exceptions;

import com.example.restservice.Exeptions.DomainException;

public class UserNotFoundException extends DomainException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
