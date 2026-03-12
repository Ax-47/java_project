package com.example.restservice.Address.exceptions;

public class AddressNotFoundException extends RuntimeException {

  public AddressNotFoundException(String message) {
    super(message + "Not Found");
  }
}
