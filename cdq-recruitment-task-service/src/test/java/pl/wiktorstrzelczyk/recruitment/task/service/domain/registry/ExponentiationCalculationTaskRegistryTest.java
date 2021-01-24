package pl.wiktorstrzelczyk.recruitment.task.service.domain.registry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import pl.wiktorstrzelczyk.recruitment.task.api.dto.TaskInformation;
import pl.wiktorstrzelczyk.recruitment.task.service.UnitTest;
import pl.wiktorstrzelczyk.recruitment.task.service.domain.service.MonitorableFutureTask;
import pl.wiktorstrzelczyk.recruitment.task.service.helper.TaskHelper;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ExponentiationCalculationTaskRegistryTest extends UnitTest {

    @Autowired
    private ExponentiationCalculationTaskRegistry taskRegistry;

    private Map<Long, MonitorableFutureTask<BigInteger>> tasks;

    @Before
    public void setup() {
        Set<MonitorableFutureTask<BigInteger>> monitorableFutureTasks = TaskHelper.buildMonitorableFutureTasks(5);
        tasks = monitorableFutureTasks.stream().collect(Collectors.toMap(task -> taskRegistry.registerNewTask(task), task -> task));
    }

    @After
    public void cleanUp() {
        taskRegistry.removeAllTasks();
    }

    @Test
    public void shouldGetAllTasksInformation() {
        // Given
        // When
        List<TaskInformation> taskInformation = taskRegistry.getAllTasksInformation();

        // Then
        List<TaskInformation> expectedTaskInformation = extractTaskInformationList();
        Assertions.assertEquals(taskInformation, expectedTaskInformation);
    }

    @Test
    public void shouldGetTaskInformation() {
        // Given
        Long taskId = tasks.keySet().iterator().next();

        // When
        TaskInformation taskInformation = taskRegistry.getTaskInformation(taskId);

        // Then
        TaskInformation expectedTaskInformation = extractTaskInformation(taskId);
        Assertions.assertEquals(taskInformation, expectedTaskInformation);
    }

    @Test
    public void shouldGetTask() {
        // Given
        Long taskId = tasks.keySet().iterator().next();

        // When
        MonitorableFutureTask<BigInteger> monitorableFutureTask = taskRegistry.getTask(taskId);

        // Then
        TaskInformation expectedTaskInformation = extractTaskInformation(taskId);
        Assertions.assertEquals(monitorableFutureTask, tasks.get(taskId));
    }

    @Test
    public void shouldRegisterNewTask() {
        // Given
        MonitorableFutureTask<BigInteger> newTask = TaskHelper.buildMonitorableFutureTask(5, 3);

        // When
        Long newTaskId = taskRegistry.registerNewTask(newTask);

        // Then
        Assertions.assertEquals(taskRegistry.getTask(newTaskId), newTask);
        Assertions.assertEquals(taskRegistry.getAllTasksInformation().size(), tasks.size() + 1);
    }

    @Test
    public void shouldRemoveTask() {
        // Given
        Long taskId = tasks.keySet().iterator().next();

        // When
        taskRegistry.removeTask(taskId);

        // Then
        Set<Long> fetchedTaskInformationIds = fetchTaskInformationIds();
        Assertions.assertFalse(fetchedTaskInformationIds.contains(taskId));
        Assertions.assertEquals(taskRegistry.getAllTasksInformation().size(), tasks.size() - 1);
    }

    @Test
    public void shouldRemoveAllTasks() {
        // Given

        // When
        taskRegistry.removeAllTasks();

        // Then
        Assertions.assertTrue(taskRegistry.getAllTasksInformation().isEmpty());
    }

    private Set<Long> fetchTaskInformationIds() {
        return taskRegistry.getAllTasksInformation().stream()
                .map(TaskInformation::getId)
                .collect(Collectors.toSet());
    }

    private List<TaskInformation> extractTaskInformationList() {
        return tasks.entrySet().stream()
                .map(task -> TaskInformation.of(task.getKey(), task.getValue().getProgress(), task.getValue().getStatus().name(), null))
                .collect(Collectors.toList());
    }

    private TaskInformation extractTaskInformation(Long taskId) {
        return extractTaskInformationList().stream()
                .filter(task -> task.getId().equals(taskId))
                .findAny().orElse(null);
    }
}