package com.jira.controller;

import com.jira.constants.RouteConstants;
import com.jira.request.LoginRequest;
import com.jira.services.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Hidden
@RestController
@RequestMapping(RouteConstants.BASE_URL + RouteConstants.AUTH)
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);

    }
}
