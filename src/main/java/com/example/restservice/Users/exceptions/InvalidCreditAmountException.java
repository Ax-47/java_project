package com.example.restservice.Users.exceptions;

public class InvalidCreditAmountException extends RuntimeException {
  public InvalidCreditAmountException() {
    super("Amount must be positive");
  }
}
