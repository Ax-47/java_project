package com.example.restservice.Address.exceptions;

public class UnauthorizedAddressActionException extends RuntimeException {

    public UnauthorizedAddressActionException(String message) {
        super(message + "Unauthorized Action");
    }
}
