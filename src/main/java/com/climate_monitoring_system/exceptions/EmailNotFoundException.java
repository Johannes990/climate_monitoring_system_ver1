package com.climate_monitoring_system.exceptions;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException() {

    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
