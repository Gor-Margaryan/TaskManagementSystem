package com.jira;

public abstract class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
