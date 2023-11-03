package dev.sergevas.iot.growlabv1.health.application.port.in;

import dev.sergevas.iot.growlabv1.health.domain.SystemInfo;

public interface SystemInfoUseCase {

    SystemInfo getSystemInfo();
}
