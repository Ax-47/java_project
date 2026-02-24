package com.example.restservice.Users.exeptions;

public class CreditCannotBeNegativeException extends RuntimeException {
  public CreditCannotBeNegativeException() {
    super("Credit cannot be negative");
  }
}
