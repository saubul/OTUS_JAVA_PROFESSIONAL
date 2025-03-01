package ru.otus.java.pro.mt.core.transfers.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class UnsuccessTransfersMetricsService {
    private final Counter counter;
    public UnsuccessTransfersMetricsService(MeterRegistry meterRegistry) {
        counter = Counter.builder("unsuccess_transfers_metrics")
                .tags("environment", "development")
                .register(meterRegistry);
    }
    public void increment() {
        counter.increment();
    }
}
