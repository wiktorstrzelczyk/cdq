package pl.wiktorstrzelczyk.recruitment.task.service.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class Problem {

    private OffsetDateTime timestamp;
    private String error;
    private String message;
    private String path;
}
