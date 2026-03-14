package dev.sergevas.iot.growlabv1.sender.control;

import dev.sergevas.iot.growlabv1.camera.boundary.PiCamAdapter;
import dev.sergevas.iot.growlabv1.sender.boundary.SensorDataFileDumpAdapter;
import dev.sergevas.iot.growlabv1.shared.controller.HelidonConfigHandler;
import io.helidon.config.Config;

public class SensorSchedulerService {

    private SensorDataFileDumpAdapter sensorDataFileDumpAdapter;
    private SensorDataReadingService sensorDataReadingService;
    private PiCamAdapter piCamAdapter;


    public SensorSchedulerService(Config config) {
        this.sensorDataFileDumpAdapter = SensorDataFileDumpAdapter
                .create(HelidonConfigHandler.getConfigMap(config, "file"));
        this.sensorDataReadingService = new SensorDataReadingService(config);
        this.piCamAdapter = PiCamAdapter.create();
        this.piCamAdapter.installNativeLib();
        this.piCamAdapter.createCameraConfiguration(config);
    }


}

