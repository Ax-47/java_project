package com.example.restservice.Users.exceptions;

import com.example.restservice.Exeptions.DomainException;

public class CreditCannotBeNegativeException extends DomainException {
  public CreditCannotBeNegativeException() {
    super("Credit cannot be negative");
  }
}
