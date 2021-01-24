package pl.wiktorstrzelczyk.recruitment.task.service.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wiktorstrzelczyk.recruitment.task.api.dto.*;
import pl.wiktorstrzelczyk.recruitment.task.api.request.TaskCreateRequest;
import pl.wiktorstrzelczyk.recruitment.task.service.domain.registry.ExponentiationCalculationTaskRegistry;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class ExponentiationApiServiceImpl implements ExponentiationApiService {

    private final ExecutorService taskExecutor;
    private final ExponentiationCalculationTaskRegistry taskRegistry;

    @Override
    public Long registerTask(TaskCreateRequest request) {
        ExponentiationCalculationCallable powerCalculationTask = new ExponentiationCalculationCallable(request.getBase(), request.getExponent());
        MonitorableFutureTask<BigInteger> task = new MonitorableFutureTask(powerCalculationTask);
        return taskRegistry.registerNewTask(task);
    }

    @Override
    public void executeTask(Long taskId) {
        MonitorableFutureTask<BigInteger> task = taskRegistry.getTask(taskId);
        taskExecutor.submit(task);
    }

    @Override
    public TaskInformation get(Long taskId) {
       return taskRegistry.getTaskInformation(taskId);
    }

    @Override
    public List<TaskInformation> find() {
       return taskRegistry.getAllTasksInformation();
    }
}
