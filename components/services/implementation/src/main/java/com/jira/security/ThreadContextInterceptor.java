package com.jira.security;

import com.jira.entity.TaskEntity;
import com.jira.exceptions.task_exception.TaskApiException;
import com.jira.repository.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;
import java.util.UUID;

@Configuration
public class ThreadContextInterceptor implements HandlerInterceptor {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            RequiredCreatorUser methodAnnotation = handlerMethod.getMethodAnnotation(RequiredCreatorUser.class);
            if (methodAnnotation != null) {
                String bearerToken = request.getHeader("Authorization");
                if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                    String token = bearerToken.substring("Bearer ".length());
                    String emailFromToken = getEmailFromToken(token);
                    String requestURI = request.getRequestURI();
                    String id = extractIdFromPath(requestURI);
                    UUID uuid = UUID.fromString(id);
                    TaskEntity taskEntity = taskRepository.findById(uuid).orElseThrow(
                            () -> new TaskApiException("Problem during get Task"));
                    if (!taskEntity.getCreator().equalsIgnoreCase(emailFromToken)){
                        throw new SecurityForbiddenException("You dont have permission to access this resource");
                    }

                }
            }
        }

     return true;
    }
    private String getEmailFromToken (String token){
        String[] split = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(split[1]));
        String s = payload.split(",")[0].split(":")[1];
        return s.substring(1, s.length() - 1);
    }
    private String extractIdFromPath(String path){
        String[] parts = path.split("/");
        return parts[parts.length - 1];
    }
}