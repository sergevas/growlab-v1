package dev.sergevas.iot.growlabv1.bh1750.application.service;

import dev.sergevas.iot.growlabv1.bh1750.application.port.out.LightIntensity;
import dev.sergevas.iot.growlabv1.bh1750.domain.Bh1750Readings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Bh1750Service {
    private final LightIntensity lightIntensity;

    @Inject
    public Bh1750Service(LightIntensity lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public Bh1750Readings getSensorReadingsItemTypeForBh1750() {
        return new Bh1750Readings().lightIntensity(lightIntensity.getLightIntensity());
    }
}
