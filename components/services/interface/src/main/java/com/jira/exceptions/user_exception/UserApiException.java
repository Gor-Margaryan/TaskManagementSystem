package com.jira.exceptions.user_exception;

import com.jira.ApiException;

public class UserApiException extends ApiException {
    public UserApiException(String message) {
        super(message);
    }
    public UserApiException(String message, Throwable throwable) {
      super(message, throwable);
    }
}
