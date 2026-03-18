package com.example.restservice.Users.exceptions;

import com.example.restservice.Exeptions.DomainException;

public class InvalidPasswordException extends DomainException {
  public InvalidPasswordException(String message) {
    super(message);
  }
}
