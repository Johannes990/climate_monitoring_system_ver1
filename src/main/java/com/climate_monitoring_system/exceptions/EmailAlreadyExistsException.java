package com.climate_monitoring_system.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {

    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
