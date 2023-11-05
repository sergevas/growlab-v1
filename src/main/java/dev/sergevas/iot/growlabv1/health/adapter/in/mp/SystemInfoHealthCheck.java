package dev.sergevas.iot.growlabv1.health.adapter.in.mp;


import dev.sergevas.iot.growlabv1.health.application.port.in.SystemInfoUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@ApplicationScoped
@Liveness
public class SystemInfoHealthCheck implements HealthCheck {

    private static final String HEALTH_CHECK_NAME = "systemInfo";
    private static final String CPU_TEMP = "cpuTemp";

    @Inject
    SystemInfoUseCase systemInfoUseCase;

    @Override
    public HealthCheckResponse call() {
        var systemInfo = systemInfoUseCase.getSystemInfo();
        return HealthCheckResponse
                .named(HEALTH_CHECK_NAME)
                .up()
                .withData(CPU_TEMP, String.valueOf(systemInfo.cpuTemp()))
                .build();
    }
}
