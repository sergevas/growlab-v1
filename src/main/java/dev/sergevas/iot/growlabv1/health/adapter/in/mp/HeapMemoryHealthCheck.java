package dev.sergevas.iot.growlabv1.health.adapter.in.mp;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

/**
 * This is a modified copy of io.helidon.health.checks.HeapMemoryHealthCheck
 */
@ApplicationScoped
@Liveness
public class HeapMemoryHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return null;
    }
}
