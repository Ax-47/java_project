package com.example.restservice.Products.exceptions;

public class PriceCannotBeNegativeException extends RuntimeException {
  public PriceCannotBeNegativeException() {
    super("Credit cannot be negative");
  }
}
