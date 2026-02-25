package com.example.restservice.Users.exceptions;

public class InvalidUserNameException extends RuntimeException {
  public InvalidUserNameException(String message) {
    super(message);
  }
}
