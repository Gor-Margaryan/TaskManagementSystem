package com.jira.exceptions.task_exception;

import com.jira.BadRequestException;

public class TaskBadRequestException extends BadRequestException {
    public TaskBadRequestException(String message) {
        super(message);
    }
}
