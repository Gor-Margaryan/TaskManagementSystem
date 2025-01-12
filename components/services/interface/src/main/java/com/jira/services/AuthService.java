package com.jira.services;

import com.jira.request.LoginRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);
}
