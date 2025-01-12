package com.jira.entity;

import com.jira.UserRequest;
import com.jira.constants.DataBaseConstants;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = DataBaseConstants.SCHEMA_NAME, name = DataBaseConstants.USER_TABLE)
public class UserEntity {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;

    public UserEntity(UserRequest userRequest){
        id = userRequest.getId();
        name = userRequest.getName();
        surname = userRequest.getSurname();
        email = userRequest.getEmail();
        password = userRequest.getPassword();
    }
    public UserRequest toUserRequest(){
        return new UserRequest(id, name, surname, email, password);
    }
}
