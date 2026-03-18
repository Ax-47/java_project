package com.example.restservice.Users.exceptions;

import com.example.restservice.Exeptions.DomainException;

public class InvalidCreditAmountException extends DomainException {
  public InvalidCreditAmountException() {
    super("Amount must be positive");
  }
}
