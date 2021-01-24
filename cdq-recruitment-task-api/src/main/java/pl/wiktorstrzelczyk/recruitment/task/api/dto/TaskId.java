package pl.wiktorstrzelczyk.recruitment.task.api.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TaskId {
    @NotNull
    private Long id;
}
