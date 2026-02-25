package com.example.restservice.Users.exceptions;

public class InsufficientCreditException extends RuntimeException {
  public InsufficientCreditException() {
    super("Insufficient credit");
  }
}
