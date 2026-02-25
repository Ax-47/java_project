package com.example.restservice.Orders.exceptions;

import com.example.restservice.Exeptions.DomainException;

public class AddressValidationException extends DomainException {

  private final String field;

  public AddressValidationException(String field, String message) {
    super(message);
    this.field = field;
  }

  public String getField() {
    return field;
  }
}
