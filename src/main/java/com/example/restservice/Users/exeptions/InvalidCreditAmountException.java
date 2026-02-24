package com.example.restservice.Users.exeptions;

public class InvalidCreditAmountException extends RuntimeException {
  public InvalidCreditAmountException() {
    super("Amount must be positive");
  }
}
