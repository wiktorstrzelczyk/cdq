package pl.wiktorstrzelczyk.recruitment.task.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long taskId) {
        super(String.format("Task with id (%s) not found", taskId));
    }
}