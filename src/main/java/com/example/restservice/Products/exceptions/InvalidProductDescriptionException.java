package com.example.restservice.Products.exceptions;

public class InvalidProductDescriptionException extends RuntimeException {

  public InvalidProductDescriptionException(String message) {
    super(message);
  }
}
