package com.jira;

public abstract class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
  public ApiException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
