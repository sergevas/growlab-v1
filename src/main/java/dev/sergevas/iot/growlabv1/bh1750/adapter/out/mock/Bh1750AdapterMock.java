package dev.sergevas.iot.growlabv1.bh1750.adapter.out.mock;

import dev.sergevas.iot.growlabv1.bh1750.application.port.out.LightIntensity;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class Bh1750AdapterMock implements LightIntensity {

    @Override
    public double getLightIntensity() {
        return 567.89;
    }
}
