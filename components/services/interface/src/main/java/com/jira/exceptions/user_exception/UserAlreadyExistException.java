package com.jira.exceptions.user_exception;

import com.jira.ResourceAlreadyException;

public class UserAlreadyExistException extends ResourceAlreadyException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
