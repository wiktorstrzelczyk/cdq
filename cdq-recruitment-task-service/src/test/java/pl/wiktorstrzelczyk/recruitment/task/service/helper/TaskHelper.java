package pl.wiktorstrzelczyk.recruitment.task.service.helper;

import pl.wiktorstrzelczyk.recruitment.task.service.domain.service.ExponentiationCalculationCallable;
import pl.wiktorstrzelczyk.recruitment.task.service.domain.service.MonitorableFutureTask;

import java.math.BigInteger;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskHelper {

    public static MonitorableFutureTask<BigInteger> buildMonitorableFutureTask(Integer base, Integer exponent) {
        ExponentiationCalculationCallable powerCalculationTask = new ExponentiationCalculationCallable(base, exponent);
        return new MonitorableFutureTask<BigInteger>(powerCalculationTask);
    }

    public static Set<MonitorableFutureTask<BigInteger>> buildMonitorableFutureTasks(Integer count) {
        return IntStream.range(0, count).mapToObj(i -> buildMonitorableFutureTask(i, i + 1)).collect(Collectors.toSet());
    }
}
