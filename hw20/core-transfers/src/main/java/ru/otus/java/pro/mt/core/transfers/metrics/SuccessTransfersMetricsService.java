package ru.otus.java.pro.mt.core.transfers.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class SuccessTransfersMetricsService {
    private final Counter counter;
    public SuccessTransfersMetricsService(MeterRegistry meterRegistry) {
        counter = Counter.builder("success_transfers_metrics")
                .tags("environment", "development")
                .register(meterRegistry);
    }
    public void increment() {
        counter.increment();
    }
}
