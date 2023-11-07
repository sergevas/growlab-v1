package dev.sergevas.iot.growlabv1.health.adapter.out.mock;

import dev.sergevas.iot.growlabv1.health.application.port.out.CpuTempFetcher;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class CpuTempAdapterMock implements CpuTempFetcher {
    @Override
    public double getCpuTemp() {
        return 52.7;
    }
}
