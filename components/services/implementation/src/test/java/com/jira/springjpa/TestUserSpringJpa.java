package com.jira.springjpa;

import com.jira.BadRequestException;
import com.jira.UserRequest;
import com.jira.entity.UserEntity;
import com.jira.exceptions.user_exception.UserBadRequestException;
import com.jira.repository.UserRepository;
import com.jira.spring_jpa.UserSpringJpa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class TestUserSpringJpa {
    @Mock
    public UserRepository userRepository;

    @Mock
    public PasswordEncoder passwordEncoder;

    @InjectMocks
    public UserSpringJpa userService;

    public UserRequest userWithNullId = UserRequest.builder()
            .name("Hayko")
            .surname("Grigoryan")
            .email("hayko@gmail.com")
            .password("Password_123")
            .build();

    public UserEntity userWithNonNullId = UserEntity.builder()
            .id(UUID.randomUUID())
            .name("Hayko")
            .surname("Grigoryan")
            .email("hayko@gmail.com")
            .password("Password_123")
            .build();


    @Test
    void testCreateUser() {
        given(userRepository.save(any(UserEntity.class))).willReturn(userWithNonNullId);
        UserRequest user = userService.createUser(userWithNullId);
        assertEquals(user.getId(),userWithNonNullId.getId());
        assertEquals(user.getName(),userWithNonNullId.getName());
        assertEquals(user.getSurname(),userWithNonNullId.getSurname());
        assertEquals(user.getEmail(),userWithNonNullId.getEmail());
        assertEquals(user.getPassword(),userWithNonNullId.getPassword());

        userWithNullId.setId(UUID.randomUUID());
        Assertions.assertThrows(UserBadRequestException.class,() ->
                userService.createUser(userWithNullId));

        userWithNullId.setId(null);
        userWithNullId.setPassword("password_123");
        Assertions.assertThrows(UserBadRequestException.class,() ->
                userService.createUser(userWithNullId));
        userWithNullId.setPassword("Password_123");


    }

}
