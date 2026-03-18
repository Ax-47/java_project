package com.example.restservice.Users.exceptions;

import com.example.restservice.Exeptions.DomainException;

public class InvalidUserNameException extends DomainException {
  public InvalidUserNameException(String message) {
    super(message);
  }
}
