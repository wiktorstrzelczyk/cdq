package pl.wiktorstrzelczyk.recruitment.task.service.domain.registry;

import pl.wiktorstrzelczyk.recruitment.task.api.dto.TaskInformation;
import pl.wiktorstrzelczyk.recruitment.task.service.domain.service.MonitorableFutureTask;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface ExponentiationCalculationTaskRegistry {
    List<TaskInformation> getAllTasksInformation();
    TaskInformation getTaskInformation(@NotNull Long taskId);
    MonitorableFutureTask<BigInteger> getTask(@NotNull Long taskId);
    Long registerNewTask(@NotNull MonitorableFutureTask<BigInteger> monitorableCallable);
    void removeTask(@NotNull Long taskId);
    void removeAllTasks();
}
