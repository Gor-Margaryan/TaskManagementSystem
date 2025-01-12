package com.jira;

public abstract class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
