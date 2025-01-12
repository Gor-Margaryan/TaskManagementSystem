package com.jira.entity;

import com.jira.constants.DataBaseConstants;
import com.jira.enums.Priority;
import com.jira.enums.Status;
import com.jira.request.TaskRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = DataBaseConstants.SCHEMA_NAME, name = DataBaseConstants.TASK_TABLE)
public class TaskEntity {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    private String creator;
    private UUID performerid;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private String name;
    private String description;
    private String comment;

    public TaskEntity(TaskRequest taskRequest) {
        id = taskRequest.getId();
        creator = taskRequest.getCreator();
        performerid = taskRequest.getPerformerId();
        status = taskRequest.getStatus();
        priority = taskRequest.getPriority();
        name = taskRequest.getName();
        description = taskRequest.getDescription();
        comment = taskRequest.getComment();

    }
    public TaskRequest toTaskRequest() {
        return new TaskRequest(id, creator, performerid, status, priority, name, description, comment);
    }
}
