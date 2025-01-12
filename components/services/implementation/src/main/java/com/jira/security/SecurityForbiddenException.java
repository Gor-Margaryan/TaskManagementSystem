package com.jira.security;

import com.jira.ForbiddenException;

public class SecurityForbiddenException extends ForbiddenException {
    public SecurityForbiddenException(String errorMessage) {
        super(errorMessage);
    }
}
