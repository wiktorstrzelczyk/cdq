package pl.wiktorstrzelczyk.recruitment.task.service.domain.service;

import pl.wiktorstrzelczyk.recruitment.task.service.domain.common.Status;

import java.math.BigInteger;
import java.util.Objects;

public class ExponentiationCalculationCallable implements MonitorableCallable<BigInteger> {

    private final Integer base;
    private final Integer exponent;
    private int progress;
    private Status status;

    public ExponentiationCalculationCallable(Integer base, Integer exponent) {
        this.base = base;
        this.exponent = exponent;
        this.status = Status.NOT_STARTED;
        checkInvariants();
    }

    private void checkInvariants() {
        Objects.requireNonNull(base);
        Objects.requireNonNull(exponent);
        if(base < 0 || exponent < 0) {
            throw new IllegalArgumentException("Base and exponent should be positive numbers");
        }
    }

    @Override
    public BigInteger call() {
        status = Status.RUNNING;
        BigInteger result = calculate();
        status = Status.FINISHED;
        return result;
    }

    private BigInteger calculate() {
        BigInteger baseBigInteger = BigInteger.valueOf(base);
        BigInteger result = BigInteger.ONE;
        for(int i = 1; i <= exponent; i++) {
            result = result.multiply(baseBigInteger);
            progress =  (int)((i * 100d) / exponent);
        }
        return result;
    }

    @Override
    public int getProgress() {
        return progress;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}
