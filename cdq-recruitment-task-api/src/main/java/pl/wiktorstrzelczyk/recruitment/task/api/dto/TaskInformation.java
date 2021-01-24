package pl.wiktorstrzelczyk.recruitment.task.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TaskInformation {

    @NotNull
    private final Long id;
    private final String progress;
    @NotBlank
    private final String status;
    private final BigInteger result;

    public static TaskInformation of(Long id, Integer progress, String status, BigInteger result) {
        return TaskInformation.builder()
                    .id(id)
                    .progress(progress + "%")
                    .status(status)
                    .result(result)
                    .build();
    }


}
