package com.example.restservice.Users.exceptions;

import com.example.restservice.Exeptions.DomainException;

public class InsufficientCreditException extends DomainException {
  public InsufficientCreditException() {
    super("เงินไม่พอจ้า");
  }
}
