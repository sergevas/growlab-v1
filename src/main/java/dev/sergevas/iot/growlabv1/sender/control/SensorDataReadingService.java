package dev.sergevas.iot.growlabv1.sender.control;

import dev.sergevas.iot.growlabv1.bh1750.boundary.Bh1750Adapter;
import dev.sergevas.iot.growlabv1.bme280.boundary.Bmep280Adapter;
import dev.sergevas.iot.growlabv1.camera.boundary.CameraModeControlAdapter;
import dev.sergevas.iot.growlabv1.sender.entity.SensorReadings;
import dev.sergevas.iot.growlabv1.shared.controller.HelidonConfigHandler;
import io.helidon.config.Config;

import java.time.Instant;

public class SensorDataReadingService {

    private final Bh1750Adapter bh1750Adapter;
    private final Bmep280Adapter bmep280Adapter;
    private final CameraModeControlAdapter cameraModeControlAdapter;

    public SensorDataReadingService(Config config) {
        bh1750Adapter = Bh1750Adapter.create(HelidonConfigHandler.getConfigMap(config, "bh1750"));
        bmep280Adapter = Bmep280Adapter.create(HelidonConfigHandler.getConfigMap(config, "bmep280"));
        cameraModeControlAdapter = CameraModeControlAdapter.create(HelidonConfigHandler.getConfigMap(config, "camera"));
    }

    public SensorReadings readAllSensorData() {
        var thpReadings = bmep280Adapter.getThpReadings();
        return new SensorReadings(
                thpReadings.getTemperature(),
                thpReadings.getHumidity(),
                thpReadings.getPressure(),
                bh1750Adapter.getLightIntensity(),
                Instant.now(),
                cameraModeControlAdapter.getMode()
        );
    }
}
