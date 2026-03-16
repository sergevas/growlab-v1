package dev.sergevas.iot.growlabv1.sender.control;

import dev.sergevas.iot.growlabv1.camera.boundary.PiCamAdapter;
import dev.sergevas.iot.growlabv1.renderer.control.ImageOverlayService;
import dev.sergevas.iot.growlabv1.sender.boundary.SensorDataFileDumpAdapter;
import dev.sergevas.iot.growlabv1.shared.controller.HelidonConfigHandler;
import io.helidon.config.Config;

import java.util.logging.Logger;

public class SensorSchedulerService {

    private static final Logger LOG = Logger.getLogger(SensorSchedulerService.class.getName());

    private final SensorDataFileDumpAdapter sensorDataFileDumpAdapter;
    private final SensorDataReadingService sensorDataReadingService;
    private final PiCamAdapter piCamAdapter;
    private final ImageOverlayService imageOverlayService;


    public SensorSchedulerService(Config config) {
        this.sensorDataFileDumpAdapter = SensorDataFileDumpAdapter
                .create(HelidonConfigHandler.getConfigMap(config, "file"));
        this.sensorDataReadingService = new SensorDataReadingService(config);
        this.piCamAdapter = PiCamAdapter.create();
        this.imageOverlayService = new ImageOverlayService();
    }

    public void processSensorData() {
        LOG.info("Entering processSensorData()");
        var readings = sensorDataReadingService.readAllSensorData();
        var camImage = piCamAdapter.takePictureWithCamRecover();
        var overlayImage = imageOverlayService.render(camImage, readings.toStringFormatted());
        sensorDataFileDumpAdapter.writeDataToFile(readings, overlayImage);
        LOG.info("Exiting processSensorData()");
    }
}
