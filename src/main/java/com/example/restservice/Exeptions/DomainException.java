package com.example.restservice.Exeptions;

public abstract class DomainException extends RuntimeException {

  public DomainException(String message) {
    super(message);
  }
}
