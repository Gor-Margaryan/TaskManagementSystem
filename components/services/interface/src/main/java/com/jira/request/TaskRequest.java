package com.jira.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jira.enums.Priority;
import com.jira.enums.Status;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskRequest {
    @Hidden
    private UUID id;
    @Hidden
    private String creator;
    private UUID performerId;
    @Hidden
    private Status status;
    private Priority priority;
    @NotEmpty(message = "Task name can not be null")
    @Schema(example = "Create new service",description = "The name of the task")
    private String name;
    @NotEmpty(message = "Task description can not be null")
    @Schema(example = "Service must be ...", description = "The description of the task")
    private String description;
    @Schema(example = "If you can ...",description = "You can add comment for task")
    private String comment;
}
