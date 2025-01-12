package com.jira;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {

    public static final String USER_NAME_REGEX = "[A-Z][a-z]+";
    public static final String USER_NAME_REGEX_MSG = "User name permitted characters: A_Z, a-z";
    public static final String USER_NAME_NULL_MSG = "User name can not be null or empty";


    public static final String USER_SURNAME_REGEX = "[A-Z][a-z]+";
    public static final String USER_SURNAME_REGEX_MSG = "User surname permitted characters: A_Z, a-z";
    public static final String USER_SURNAME_NULL_MSG = "User surname can not be null or empty";


    public static final String USER_EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String USER_EMAIL_REGEX_MSG = "Wrong format of email";
    public static final String USER_EMAIL_NULL_MSG = "User email can not be null or empty";



    @Hidden
    private UUID id;
    @NotEmpty(message = USER_NAME_NULL_MSG)
    @Pattern(regexp = USER_NAME_REGEX, message = USER_NAME_REGEX_MSG)
    @Schema(example = "Gor", description = "The name of user")
    private String name;
    @NotEmpty(message = USER_SURNAME_NULL_MSG)
    @Pattern(regexp = USER_SURNAME_REGEX, message = USER_SURNAME_REGEX_MSG)
    @Schema(example = "Margaryan", description = "The surname of user")
    private String surname;
    @NotEmpty(message = USER_EMAIL_NULL_MSG)
    @Pattern(regexp = USER_EMAIL_REGEX, message = USER_EMAIL_REGEX_MSG)
    @Schema(example = "gor.margaryan700@gmail.com", description = "The email of user")
    private String email;
    @Schema(example = "Password_123",description = "The password of user")
    private String password;
}
