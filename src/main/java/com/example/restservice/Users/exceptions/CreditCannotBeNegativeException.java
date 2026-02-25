package com.example.restservice.Users.exceptions;

public class CreditCannotBeNegativeException extends RuntimeException {
  public CreditCannotBeNegativeException() {
    super("Credit cannot be negative");
  }
}
