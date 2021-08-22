package dev.sergevas.iot.growlabv1.hardware.boundary;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

public class SystemInfoHealthCheck implements HealthCheck {

    private static final String HEALTH_CHECK_NAME = "systemInfo";
    private static final String CPU_TEMP = "cpuTemp";

    private SystemInfoAdapter systemInfoAdapter;

    public SystemInfoHealthCheck systemInfoAdapter(SystemInfoAdapter systemInfoAdapter) {
        this.systemInfoAdapter = systemInfoAdapter;
        return this;
    }

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse
                .named(HEALTH_CHECK_NAME)
                .up()
                .withData(CPU_TEMP, this.systemInfoAdapter.getCpuTemp())
                .build();
    }

    public static SystemInfoHealthCheck create() {
        return new SystemInfoHealthCheck()
                .systemInfoAdapter(SystemInfoAdapter.create());
    }
}
