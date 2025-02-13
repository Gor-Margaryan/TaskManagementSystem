package com.jira.spring_jpa;

import com.jira.entity.UserEntity;
import com.jira.exceptions.user_exception.UserApiException;
import com.jira.exceptions.user_exception.UserBadRequestException;
import com.jira.repository.UserRepository;
import com.jira.request.LoginRequest;
import com.jira.security.JwtUtil;
import com.jira.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthSpringJpa implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public String login(LoginRequest loginRequest) {
        String token;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword(), new ArrayList<>()));

            String email = authentication.getName();
            UserEntity userEntity = userRepository.getByEmail(email);
            userEntity.setPassword("");
            token = jwtUtil.createToken(userEntity);
        }
        catch (BadCredentialsException ex) {
            throw new UserBadRequestException("Invalid username or password");
        }
        catch (Exception e) {
            throw new UserApiException("Problem during getting token", e);
        }
        return token;
    }
}
