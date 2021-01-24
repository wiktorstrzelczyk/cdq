package pl.wiktorstrzelczyk.recruitment.task.service.domain.service;


import pl.wiktorstrzelczyk.recruitment.task.service.domain.common.Status;

public interface Monitorable {
    int getProgress();
    Status getStatus();
}
