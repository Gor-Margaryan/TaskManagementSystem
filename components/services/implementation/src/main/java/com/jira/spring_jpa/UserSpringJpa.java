package com.jira.spring_jpa;

import com.jira.UserRequest;
import com.jira.entity.UserEntity;
import com.jira.exceptions.user_exception.UserAlreadyExistException;
import com.jira.exceptions.user_exception.UserApiException;
import com.jira.exceptions.user_exception.UserBadRequestException;
import com.jira.exceptions.user_exception.UserNotFoundException;
import com.jira.repository.UserRepository;
import com.jira.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserSpringJpa implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserRequest createUser(UserRequest userRequest) {
        if (userRequest.getId() != null) {
            throw new UserBadRequestException("User ID must be null");
        }
        String password = userRequest.getPassword();
        passwordValidation(password);
        UserEntity userEntity;
        try {
             userEntity = userRepository.getByEmail(userRequest.getEmail());
        } catch (Exception e) {
            throw new UserApiException("Problem during create user", e);
        }
        if (userEntity != null) {
            throw new UserAlreadyExistException("User already exists with given email");
        }
        userRequest.setPassword(passwordEncoder.encode(password));
        try {
            userEntity = userRepository.save(new UserEntity(userRequest));
        } catch (Exception e) {
            throw new UserApiException("Problem during create user", e);
        }

        userEntity.setPassword(null);

        return userEntity.toUserRequest();


    }

    @Override
    public UserRequest getById(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with given id"));
        userEntity.setPassword(null);
        return userEntity.toUserRequest();
    }

    @Override
    public List<UserRequest> getAllUsers() {
        List<UserEntity> userEntities;
        try {
            userEntities = userRepository.findAll();
        } catch (Exception e) {
            throw new UserApiException("Problem during getAllUsers", e);
        }
        return userEntities
                .stream()
                .map(UserEntity::toUserRequest)
                .peek(this::deletePassword)
                .toList();
    }

    @Override
    public UserRequest updateUser(UUID id, UserRequest userRequest) {

        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with given id"));
               passwordValidation(userRequest.getPassword());
        UserEntity ur;
               try {
                    ur = userRepository.getByEmailAndIdNot(userRequest.getEmail(), id);
               } catch (Exception e) {
                   throw new UserApiException("Problem during update user", e);
               }
               if (ur != null) {
                   throw new UserAlreadyExistException("User already exists with given email");
               }
               userEntity.setName(userRequest.getName());
               userEntity.setSurname(userRequest.getSurname());
               userEntity.setEmail(userRequest.getEmail());
               userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
               try {
                   userEntity = userRepository.save(userEntity);
               } catch (Exception e) {
                   throw new UserApiException("Problem during update user", e);
               }
               userEntity.setPassword(null);
               return userEntity.toUserRequest();


    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.findById(id).orElseThrow(
                ()->new UserNotFoundException("User not found with given id"));
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserApiException("Problem during delete user", e);
        }

    }



    private void passwordValidation(String password) {

        if (password.length() < 8) {
            throw new UserBadRequestException("Password must be at least 8 characters");
        }
        int upperCaseCounter = 0;
        int digitsCounter = 0;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                upperCaseCounter++;
            }
            if (Character.isDigit(c)) {
                digitsCounter++;
            }
        }
        if (upperCaseCounter < 1 || digitsCounter < 1) {
            throw new UserBadRequestException("Password must be at least minimum 1 upper case and 1 digit");
        }
    }
    private void deletePassword(UserRequest userRequest) {
        userRequest.setPassword(null);
    }
}
