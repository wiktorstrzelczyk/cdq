package pl.wiktorstrzelczyk.recruitment.task.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class TaskCreateRequest {
    @NotNull
    private final Integer base;
    @NotNull
    private final Integer exponent;
}
