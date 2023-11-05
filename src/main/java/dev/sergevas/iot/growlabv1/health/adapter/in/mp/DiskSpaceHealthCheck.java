package dev.sergevas.iot.growlabv1.health.adapter.in.mp;

import dev.sergevas.iot.growlabv1.health.application.port.in.DiskSpaceUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@ApplicationScoped
@Liveness
public class DiskSpaceHealthCheck implements HealthCheck {

    private static final String HEALTH_CHECK_NAME = "diskSpace";

    @Inject
    DiskSpaceUseCase diskSpaceUseCase;

    @Override
    public HealthCheckResponse call() {
        var diskSpace = diskSpaceUseCase.getDiskSpace();
        return HealthCheckResponse
                .named(HEALTH_CHECK_NAME)
                .status(diskSpaceUseCase.isThresholdReached(diskSpace))
                .withData("freeBytes", diskSpace.diskFreeInBytes())
                .withData("free", diskSpace.free())
                .withData("percentFree", diskSpace.percentFree())
                .withData("totalBytes", diskSpace.totalInBytes())
                .withData("total", diskSpace.total())
                .build();
    }
}
