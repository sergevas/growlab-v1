package dev.sergevas.iot.growlabv1.health.application.service;

import dev.sergevas.iot.growlabv1.health.application.port.in.SystemInfoUseCase;
import dev.sergevas.iot.growlabv1.health.application.port.out.CpuTempFetcher;
import dev.sergevas.iot.growlabv1.health.domain.SystemInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SystemInfoService implements SystemInfoUseCase {
    private final CpuTempFetcher cpuTemp;

    @Inject
    public SystemInfoService(CpuTempFetcher cpuTEmp) {
        this.cpuTemp = cpuTEmp;
    }

    @Override
    public SystemInfo getSystemInfo() {
        return new SystemInfo(cpuTemp.getCpuTemp());
    }
}
