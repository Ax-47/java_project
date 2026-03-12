package com.example.restservice.Orders.exceptions;

public class UnauthorizedOrderActionException extends RuntimeException {

  public UnauthorizedOrderActionException(String massage) {
    super(massage);
  }
}
