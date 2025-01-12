package com.jira.exceptions.task_exception;

import com.jira.ApiException;

public class TaskApiException extends ApiException {
    public TaskApiException(String message) {
        super(message);
    }
    public TaskApiException(String message, Throwable throwable) {
      super(message, throwable);
    }
}
