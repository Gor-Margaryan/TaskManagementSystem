package com.jira;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetails {
    private String errorMessage;
    private int status;
    private List<String> details;

    public ErrorDetails(String errorMessage, int status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }
}
