package pl.wiktorstrzelczyk.recruitment.task.service.domain.registry;

import org.springframework.stereotype.Component;
import pl.wiktorstrzelczyk.recruitment.task.api.dto.TaskInformation;
import pl.wiktorstrzelczyk.recruitment.task.api.exception.TaskNotFoundException;
import pl.wiktorstrzelczyk.recruitment.task.service.domain.service.MonitorableFutureTask;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class ExponentiationCalculationTaskRegistryImpl implements ExponentiationCalculationTaskRegistry {

    private final ConcurrentMap<Long, MonitorableFutureTask<BigInteger>> tasks;
    private final AtomicLong idSequence;

    public ExponentiationCalculationTaskRegistryImpl() {
        this.tasks = new ConcurrentHashMap<>();
        this.idSequence = new AtomicLong();
    }

    @Override
    public List<TaskInformation> getAllTasksInformation() {
        return tasks.keySet()
                .stream()
                .map(this::getTaskInformation)
                .collect(Collectors.toList());
    }

    @Override
    public TaskInformation getTaskInformation(Long taskId) {
        MonitorableFutureTask<BigInteger> task = getTask(taskId);
        BigInteger result = null;
        try {
            result = task.isDone() ? task.get() : null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return TaskInformation.of(taskId, task.getProgress(), task.getStatus().name(), result);
    }

    @Override
    public MonitorableFutureTask<BigInteger> getTask(Long taskId) {
        return Optional.ofNullable(tasks.get(taskId)).orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public Long registerNewTask(MonitorableFutureTask<BigInteger> task) {
        Long nextId = idSequence.incrementAndGet();
        tasks.put(nextId, task);
        return nextId;
    }

    @Override
    public void removeTask(Long taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }
}
