package pl.wiktorstrzelczyk.recruitment.task.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.wiktorstrzelczyk.recruitment.task.api.ExponentiationTaskApi;
import pl.wiktorstrzelczyk.recruitment.task.api.dto.TaskId;
import pl.wiktorstrzelczyk.recruitment.task.api.request.TaskCreateRequest;
import pl.wiktorstrzelczyk.recruitment.task.api.dto.TaskInformation;
import pl.wiktorstrzelczyk.recruitment.task.service.domain.service.ExponentiationApiService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExponentiationController implements ExponentiationTaskApi {

    private final ExponentiationApiService exponentiationApiService;

    @Override
    public TaskId create(TaskCreateRequest request) {
        Long registeredTaskId = exponentiationApiService.registerTask(request);
        exponentiationApiService.executeTask(registeredTaskId);
        return new TaskId(registeredTaskId);
    }

    @Override
    public List<TaskInformation> find() {
        return exponentiationApiService.find();
    }

    @Override
    public TaskInformation get(Long taskId) {
       return exponentiationApiService.get(taskId);
    }
}
