package dev.sergevas.iot.growlabv1.health.application.port.out;

import dev.sergevas.iot.growlabv1.health.domain.DiskSpace;

public interface DiskSpaceFetcher {

    DiskSpace getDiskSpace();
}
