package pl.wiktorstrzelczyk.recruitment.task.service.domain.service;

import java.util.concurrent.Callable;

public interface MonitorableCallable<V> extends Monitorable, Callable<V> {
}
