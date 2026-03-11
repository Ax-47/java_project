package com.example.restservice.Auth.exceptions;

public class UnauthorizedAddressActionException extends RuntimeException {

    public UnauthorizedAddressActionException(String message) {
        super(message + "Unauthorized Action");
    }
}
