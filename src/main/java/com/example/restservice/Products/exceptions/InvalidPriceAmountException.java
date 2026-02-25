package com.example.restservice.Products.exceptions;

public class InvalidPriceAmountException extends RuntimeException {
  public InvalidPriceAmountException() {
    super("Amount must be positive");
  }
}
