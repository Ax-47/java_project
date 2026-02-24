package com.example.restservice.Users.exeptions;

public class InvalidUserNameException extends RuntimeException {
  public InvalidUserNameException(String message) {
    super(message);
  }
}
