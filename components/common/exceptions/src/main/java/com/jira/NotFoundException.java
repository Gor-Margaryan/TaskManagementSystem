package com.jira;

public abstract class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
