package com.example.restservice.Products.exceptions;

public class InvalidProductNameException extends RuntimeException {

  public InvalidProductNameException(String message) {
    super(message);
  }
}
