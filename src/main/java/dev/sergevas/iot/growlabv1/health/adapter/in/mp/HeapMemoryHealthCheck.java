package dev.sergevas.iot.growlabv1.health.adapter.in.mp;

import dev.sergevas.iot.growlabv1.health.application.port.in.HeapMemoryUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

/**
 * This is a modified copy of io.helidon.health.checks.HeapMemoryHealthCheck
 */
@ApplicationScoped
@Liveness
public class HeapMemoryHealthCheck implements HealthCheck {

    static final String HEALTH_CHECK_NAME = "heapMemory";

    @Inject
    HeapMemoryUseCase heapMemoryUseCase;

    @Override
    public HealthCheckResponse call() {
        var heapMemory = heapMemoryUseCase.getHeapMemory();
        return HealthCheckResponse
                .named(HEALTH_CHECK_NAME)
                .status(heapMemoryUseCase.isThresholdReached(heapMemory))
                .withData("percentFree", heapMemory.percentFree())
                .withData("free", heapMemory.free())
                .withData("freeBytes", heapMemory.freeMemory())
                .withData("max", heapMemory.max())
                .withData("maxBytes", heapMemory.maxMemory())
                .withData("total", heapMemory.total())
                .withData("totalBytes", heapMemory.totalMemory())
                .build();
    }
}
