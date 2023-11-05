package dev.sergevas.iot.growlabv1.health.application.service;

import dev.sergevas.iot.growlabv1.health.application.port.in.DiskSpaceUseCase;
import dev.sergevas.iot.growlabv1.health.application.port.out.DiskSpaceFetcher;
import dev.sergevas.iot.growlabv1.health.domain.DiskSpace;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DiskSpaceService implements DiskSpaceUseCase {

    @ConfigProperty(name = "growlabv1.healthcheck.diskSpace.thresholdPercent", defaultValue = "99.999")
    double diskSpaceThresholdPercent;

    @Inject
    DiskSpaceFetcher diskSpaceFetcher;

    @Override
    public DiskSpace getDiskSpace() {
        return diskSpaceFetcher.getDiskSpace();
    }

    public boolean isThresholdReached(DiskSpace diskSpace) {
        long threshold = (long) ((diskSpaceThresholdPercent / 100) * diskSpace.totalInBytes());
        return threshold >= diskSpace.usedInBytes();
    }
}
