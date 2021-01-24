package pl.wiktorstrzelczyk.recruitment.task.service.domain.service;

import pl.wiktorstrzelczyk.recruitment.task.api.request.TaskCreateRequest;
import pl.wiktorstrzelczyk.recruitment.task.api.dto.TaskInformation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface ExponentiationApiService {
    Long registerTask(@NotNull @Valid TaskCreateRequest request);
    void executeTask(@NotNull Long taskId);
    TaskInformation get(@NotNull Long taskId);
    List<TaskInformation> find();
}
