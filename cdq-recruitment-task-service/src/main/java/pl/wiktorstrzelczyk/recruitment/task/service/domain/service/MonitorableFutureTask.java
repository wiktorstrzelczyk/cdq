package pl.wiktorstrzelczyk.recruitment.task.service.domain.service;


import pl.wiktorstrzelczyk.recruitment.task.service.domain.common.Status;

import java.util.concurrent.*;

public class MonitorableFutureTask<V> extends FutureTask<V> {

    private Monitorable monitorableTask;

    public MonitorableFutureTask(MonitorableRunnable monitorableRunnable, V result) {
        super(monitorableRunnable, result);
        this.monitorableTask = monitorableRunnable;
    }

    public MonitorableFutureTask(MonitorableCallable<V> monitorableCallable) {
        super(monitorableCallable);
        this.monitorableTask = monitorableCallable;
    }

   public int getProgress() {
        return monitorableTask.getProgress();
   }

   public Status getStatus() {
       return monitorableTask.getStatus();
   }
}
