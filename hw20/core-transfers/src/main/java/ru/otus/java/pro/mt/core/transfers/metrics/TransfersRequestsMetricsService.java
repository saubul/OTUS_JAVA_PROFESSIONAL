package ru.otus.java.pro.mt.core.transfers.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class TransfersRequestsMetricsService {
    private final Counter counter;
    public TransfersRequestsMetricsService(MeterRegistry meterRegistry) {
        counter = Counter.builder("transfers_requests_counter")
                .tags("environment", "development")
                .register(meterRegistry);
    }
    public void increment() {
        counter.increment();
    }
}
