package pl.wiktorstrzelczyk.recruitment.task.service.controller;

import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.wiktorstrzelczyk.recruitment.task.api.dto.TaskId;
import pl.wiktorstrzelczyk.recruitment.task.api.dto.TaskInformation;
import pl.wiktorstrzelczyk.recruitment.task.api.exception.TaskNotFoundException;
import pl.wiktorstrzelczyk.recruitment.task.api.request.TaskCreateRequest;
import pl.wiktorstrzelczyk.recruitment.task.service.UnitTest;
import pl.wiktorstrzelczyk.recruitment.task.service.domain.common.Status;
import pl.wiktorstrzelczyk.recruitment.task.service.domain.registry.ExponentiationCalculationTaskRegistry;
import pl.wiktorstrzelczyk.recruitment.task.service.domain.service.ExponentiationApiService;
import pl.wiktorstrzelczyk.recruitment.task.service.utils.Problem;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;

public class ExponentiationControllerTest extends UnitTest {

    private final String BASE_URL = "/tasks";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ExponentiationApiService exponentiationApiService;

    @Autowired
    private ExponentiationCalculationTaskRegistry exponentiationCalculationTaskRegistry;

    @BeforeEach
    public void clean() {
        exponentiationCalculationTaskRegistry.removeAllTasks();
    }

    @After
    public void cleanUp() {
        exponentiationCalculationTaskRegistry.removeAllTasks();
    }

    @Test
    public void shouldCreateTask() {
        // Given
        TaskCreateRequest request = new TaskCreateRequest(3, 2);

        // When
        ResponseEntity<TaskId> response = restTemplate.postForEntity(BASE_URL, new HttpEntity<>(request), TaskId.class);

        // Then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        TaskInformation fetchedTaskInformation = exponentiationApiService.get(response.getBody().getId());
        Assertions.assertEquals(response.getBody().getId(), fetchedTaskInformation.getId());
    }

    @Test
    public void shouldFindTasks() {
        // Given
        Long taskId = exponentiationApiService.registerTask(new TaskCreateRequest(3, 2));
        exponentiationApiService.executeTask(taskId);
        Long taskId2 = exponentiationApiService.registerTask(new TaskCreateRequest(5, 3));
        exponentiationApiService.executeTask(taskId2);

        // When
        ResponseEntity<List<TaskInformation>> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<TaskInformation>>() {
        });

        // Then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        List<Long> responseTaskIds = response.getBody().stream().map(TaskInformation::getId).collect(Collectors.toList());
        Assertions.assertEquals(response.getBody().size(), 2);
        Assertions.assertEquals(responseTaskIds, List.of(taskId, taskId2));
    }

    @Test
    public void shouldGetTask() {
        // Given
        Long taskId = exponentiationApiService.registerTask(new TaskCreateRequest(3, 2));
        exponentiationApiService.executeTask(taskId);

        // When
        ResponseEntity<TaskInformation> response = restTemplate.getForEntity(BASE_URL + "/" + taskId, TaskInformation.class);

        // Then
        await().until(() -> exponentiationCalculationTaskRegistry.getTask(taskId).isDone());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getId(), taskId);
        Assertions.assertEquals(response.getBody().getProgress(), "100%");
        Assertions.assertEquals(response.getBody().getStatus(), Status.FINISHED.name());
        Assertions.assertEquals(response.getBody().getResult(), BigInteger.valueOf(9));
    }

    @Test
    public void shouldThrowExceptionIfTaskNotFound() {
        // Given
        long notExistingTaskId = 56;

        // When
        ResponseEntity<Problem> response = restTemplate.getForEntity(BASE_URL + "/" + notExistingTaskId, Problem.class);

        // Then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getMessage(), new TaskNotFoundException(notExistingTaskId).getMessage());
    }

}
