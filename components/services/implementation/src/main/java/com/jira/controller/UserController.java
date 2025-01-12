package com.jira.controller;

import com.jira.UserRequest;
import com.jira.constants.RouteConstants;
import com.jira.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RouteConstants.BASE_URL + "${user.service.version}" + RouteConstants.USERS)
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public UserRequest createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/{id}")
    public UserRequest getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserRequest> getAll() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserRequest updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequest userRequest) {
      return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
       userService.deleteUser(id);

    }
}
