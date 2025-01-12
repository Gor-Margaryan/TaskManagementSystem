package com.jira.services;

import com.jira.enums.Status;
import com.jira.request.TaskRequest;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskRequest createTask(TaskRequest taskRequest,String email);
    List<TaskRequest> getAllTasks();
    TaskRequest getTaskById(UUID id);
    void deleteTaskById(UUID id);
    TaskRequest updateTask(UUID id, TaskRequest taskRequest);
    TaskRequest updateTaskStatus(UUID id, Status status);
    List<TaskRequest> getTasksByPerformer(UUID id);
}
