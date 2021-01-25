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

    private final ExecutorService executorService;
    private final ExponentiationCalculationTaskRegistry exponentiationTaskRegistry;

    @Override
    public Long registerTask(TaskCreateRequest request) {
        Integer base = request.getBase();
        Integer exponent = request.getExponent();
        ExponentiationCalculationCallable exponentiationCallable = new ExponentiationCalculationCallable(base, exponent);
        MonitorableFutureTask<BigInteger> exponentiationTask = new MonitorableFutureTask(exponentiationCallable);
        return exponentiationTaskRegistry.registerNewTask(exponentiationTask);
    }

    @Override
    public void executeTask(Long taskId) {
        MonitorableFutureTask<BigInteger> exponentiationTask = exponentiationTaskRegistry.getTask(taskId);
        executorService.submit(exponentiationTask);
    }

    @Override
    public TaskInformation get(Long taskId) {
       return exponentiationTaskRegistry.getTaskInformation(taskId);
    }

    @Override
    public List<TaskInformation> find() {
       return exponentiationTaskRegistry.getAllTasksInformation();
    }
}
