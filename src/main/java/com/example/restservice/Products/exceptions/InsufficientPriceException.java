package com.example.restservice.Products.exceptions;

public class InsufficientPriceException extends RuntimeException {
  public InsufficientPriceException() {
    super("Insufficient credit");
  }
}
