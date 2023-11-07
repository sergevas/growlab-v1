package dev.sergevas.iot.growlabv1.health.application.service;

import dev.sergevas.iot.growlabv1.health.application.port.in.HeapMemoryUseCase;
import dev.sergevas.iot.growlabv1.health.application.port.out.HeapMemoryFetcher;
import dev.sergevas.iot.growlabv1.health.domain.HeapMemory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class HeapMemoryService implements HeapMemoryUseCase {

    @ConfigProperty(name = "growlabv1.healthcheck.heapMemory.thresholdPercent", defaultValue = "98")
    double heapMemoryThresholdPercent;
    @Inject
    HeapMemoryFetcher heapMemoryFetcher;

    @Override
    public HeapMemory getHeapMemory() {
        return heapMemoryFetcher.getHeapMemory();
    }

    public boolean isThresholdReached(HeapMemory heapMemory) {
        long threshold = (long) ((heapMemoryThresholdPercent / 100) * heapMemory.maxMemory());
        return threshold >= heapMemory.usedMemory();
    }
}
