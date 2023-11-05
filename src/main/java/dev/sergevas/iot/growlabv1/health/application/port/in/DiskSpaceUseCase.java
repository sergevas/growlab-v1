package dev.sergevas.iot.growlabv1.health.application.port.in;

import dev.sergevas.iot.growlabv1.health.domain.DiskSpace;

public interface DiskSpaceUseCase {

    DiskSpace getDiskSpace();

    boolean isThresholdReached(DiskSpace diskSpace);
}
