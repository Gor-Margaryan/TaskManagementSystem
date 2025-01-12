package com.jira.exceptions.user_exception;

import com.jira.BadRequestException;

public class UserBadRequestException extends BadRequestException {
    public UserBadRequestException(String message) {
        super(message);
    }
}
