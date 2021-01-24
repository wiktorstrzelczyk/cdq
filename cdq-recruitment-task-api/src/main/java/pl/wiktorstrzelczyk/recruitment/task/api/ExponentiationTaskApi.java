package pl.wiktorstrzelczyk.recruitment.task.api;

import org.springframework.validation.annotation.Validated;
import pl.wiktorstrzelczyk.recruitment.task.api.dto.TaskId;
import pl.wiktorstrzelczyk.recruitment.task.api.request.TaskCreateRequest;
import org.springframework.web.bind.annotation.*;
import pl.wiktorstrzelczyk.recruitment.task.api.dto.TaskInformation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RequestMapping(value = "/tasks")
public interface ExponentiationTaskApi {

    @PostMapping
    TaskId create(@RequestBody @Valid @NotNull TaskCreateRequest request);

    @GetMapping
    List<TaskInformation> find();

    @GetMapping(value = "/{taskId}")
    TaskInformation get(@PathVariable @NotNull Long taskId);
}

