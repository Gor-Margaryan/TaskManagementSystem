package com.jira;

public abstract class ResourceAlreadyException extends RuntimeException {
    public ResourceAlreadyException(String message) {
        super(message);
    }

}
