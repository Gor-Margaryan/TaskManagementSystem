package com.jira.spring_jpa;

import com.jira.entity.TaskEntity;
import com.jira.entity.UserEntity;
import com.jira.enums.Status;
import com.jira.exceptions.task_exception.TaskApiException;
import com.jira.exceptions.task_exception.TaskBadRequestException;
import com.jira.exceptions.user_exception.UserNotFoundException;
import com.jira.repository.TaskRepository;
import com.jira.repository.UserRepository;
import com.jira.request.TaskRequest;
import com.jira.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskSpringJpa implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public TaskRequest createTask(TaskRequest taskRequest,String email) {
        validateFields(taskRequest);
        taskRequest.setCreator(email);
        taskRequest.setStatus(Status.WAITING);
        taskRequest.setName(taskRequest.getName().trim());
        taskRequest.setDescription(taskRequest.getDescription().trim());
        if (!taskRequest.getComment().isEmpty()){
            taskRequest.setComment(taskRequest.getComment().trim());
        }
        TaskEntity taskEntity;
        try {
             taskEntity = taskRepository.save(new TaskEntity(taskRequest));
        } catch (Exception e) {
            throw new TaskApiException("Problem during creating task",e);
        }
        return taskEntity.toTaskRequest();

    }

    @Override
    public List<TaskRequest> getAllTasks() {
        List<TaskEntity> taskEntities;
        try {
            taskEntities = taskRepository.findAll();
        } catch (Exception e) {
            throw new TaskApiException("Problem during getting all tasks",e);
        }
        return taskEntities
                .stream()
                .map(TaskEntity::toTaskRequest)
                .toList();


    }

    @Override
    public TaskRequest getTaskById(UUID id) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(
                () -> new TaskBadRequestException("Task not found with given id"));
        return taskEntity.toTaskRequest();
    }

    @Override
    public void deleteTaskById(UUID id) {
        taskRepository.findById(id).orElseThrow(
                () -> new TaskBadRequestException("Task not found with given id"));
        try {
            taskRepository.deleteById(id);
        }catch (Exception e) {
            throw new TaskApiException("Problem during deleting task",e);
        }
    }

    @Override
    public TaskRequest updateTask(UUID id, TaskRequest taskRequest) {
        validateFields(taskRequest);
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(
                () -> new TaskBadRequestException("Task not found with given id"));
        taskEntity.setPriority(taskRequest.getPriority());
        taskEntity.setName(taskRequest.getName().trim());
        taskEntity.setDescription(taskRequest.getDescription().trim());
        if (!taskRequest.getComment().isEmpty()){
            taskEntity.setComment(taskRequest.getComment().trim());
        }
        if (taskRequest.getPerformerId() != taskEntity.getPerformerid()){
            taskEntity.setStatus(Status.WAITING);
        }
        try {
            taskEntity = taskRepository.save(taskEntity);
        } catch (Exception e) {
            throw new TaskApiException("Problem during updating task",e);
        }
        return taskEntity.toTaskRequest();

    }

    @Override
    public TaskRequest updateTaskStatus(UUID id, Status status) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(
                () -> new TaskBadRequestException("Task not found with given id"));
        taskEntity.setStatus(status);
        try {
            taskEntity = taskRepository.save(taskEntity);
        } catch (Exception e) {
            throw new TaskApiException("Problem during updating task",e);
        }
        return taskEntity.toTaskRequest();

    }

    @Override
    public List<TaskRequest> getTasksByPerformer(UUID id) {
        List<TaskEntity> taskEntities;
        try {
           taskEntities = taskRepository.getByPerformerid(id);
        } catch (Exception e) {
            throw new TaskApiException("Problem during getting all tasks by performer",e);
        }
        if (!taskEntities.isEmpty()){
            return taskEntities
                    .stream()
                    .map(TaskEntity::toTaskRequest)
                    .toList();
        }
        return null;
    }

    private void validateFields(TaskRequest taskRequest) {
        if (taskRequest.getId() != null || taskRequest.getCreator() != null ||
        taskRequest.getStatus() != null) {
            throw new TaskBadRequestException("ID or CREATOR or STATUS must be null ");
        }
        if (taskRequest.getPerformerId() == null) {
            throw new TaskBadRequestException("Performer ID can not be null ");
        }
       userRepository.findById(taskRequest.getPerformerId()).orElseThrow(
               ()-> new UserNotFoundException("User not found with given performer id"));
    }
}
