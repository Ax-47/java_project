package com.example.restservice.Users.exeptions;

public class InsufficientCreditException extends RuntimeException {
  public InsufficientCreditException() {
    super("Insufficient credit");
  }
}
