package com.jira.controller;

import com.jira.constants.RouteConstants;
import com.jira.enums.Status;
import com.jira.request.TaskRequest;
import com.jira.security.RequiredCreatorUser;
import com.jira.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RouteConstants.BASE_URL+"${task.service.version}"+RouteConstants.TASKS)
public class TaskController {
    @Autowired
    private TaskService taskService;
    @PostMapping
    public TaskRequest createTask(@RequestBody @Valid TaskRequest taskRequest, Principal principal) {
         return taskService.createTask(taskRequest, principal.getName());
    }


    @GetMapping
    public List<TaskRequest> getAllTasks() {
        return taskService.getAllTasks();
    }


    @GetMapping("/{id}")
    public TaskRequest getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id);

    }

    @GetMapping("/performer/{id}")
    public List<TaskRequest> getTaskByPerformer(@PathVariable UUID id) {
        return taskService.getTasksByPerformer(id);
    }

    @RequiredCreatorUser
    @PutMapping("/{id}")
    public TaskRequest updateTask(@PathVariable UUID id, @RequestBody @Valid TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }


    @PatchMapping("/{id}")
    public TaskRequest updateTaskStatus(@PathVariable UUID id, @RequestParam Status status) {
        return taskService.updateTaskStatus(id, status);

    }

    @RequiredCreatorUser
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID id) {
            taskService.deleteTaskById(id);
    }

}
