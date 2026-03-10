package com.example.restservice.Products.exceptions;

public class UnauthorizedProductActionException extends RuntimeException {

    public UnauthorizedProductActionException(String message) {
        super(message);
    }
}
