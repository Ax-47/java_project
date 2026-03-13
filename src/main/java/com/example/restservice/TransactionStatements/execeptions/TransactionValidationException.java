package com.example.restservice.TransactionStatements.execeptions;

public class TransactionValidationException extends RuntimeException {

  private final String field;

  public TransactionValidationException(String field, String message) {
    super(message);
    this.field = field;
  }

  public String getField() {
    return field;
  }
}
