package com.jira.services;

import com.jira.UserRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserRequest createUser(UserRequest userRequest);
    UserRequest getById(UUID id);
    List<UserRequest> getAllUsers();
    UserRequest updateUser(UUID id, UserRequest userRequest);
    void deleteUser(UUID id);
}
