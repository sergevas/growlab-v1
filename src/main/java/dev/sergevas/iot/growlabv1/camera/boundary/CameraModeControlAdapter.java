package dev.sergevas.iot.growlabv1.camera.boundary;

import dev.sergevas.iot.growlabv1.camera.model.CameraMode;
import dev.sergevas.iot.growlabv1.hardware.boundary.PiGpioFactory;
import dev.sergevas.iot.growlabv1.shared.controller.ConfigHandler;
import dev.sergevas.iot.growlabv1.shared.exception.ActuatorException;

import java.util.Map;
import java.util.Optional;

import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_CAMERA_0002;

public class CameraModeControlAdapter {

    private static final String DIGITAL_OUTPUT_CAMERA_MODE = "digital-output-camera-control";

    private static CameraModeControlAdapter instance;

    private Integer cameraModeControlPin;
    private ConfigHandler configHandler;

    private CameraModeControlAdapter() {
        super();
    }

    public static CameraModeControlAdapter create(Map<String, String> config) {
        if (instance == null) {
            instance = new CameraModeControlAdapter()
                    .configHandler(new ConfigHandler().configMap(config));
        }
        return instance;
    }

    public CameraModeControlAdapter configHandler(ConfigHandler configHandler) {
        this.configHandler = configHandler;
        return this;
    }

    public Integer getCameraModeControlPin() {
        this.cameraModeControlPin = Optional.ofNullable(this.cameraModeControlPin)
                .orElse(this.configHandler.getAsInteger("cameraModeControlPin"));
        return cameraModeControlPin;
    }

    public CameraMode getMode() {
        CameraMode mode = null;
        try {
            var digitalOutput = PiGpioFactory
                    .createOutputInstance(DIGITAL_OUTPUT_CAMERA_MODE, this.getCameraModeControlPin());
            if (digitalOutput.isHigh()) {
                mode = CameraMode.NORM;
            } else if (digitalOutput.isLow()) {
                mode = CameraMode.NIGHT;
            } else {
                mode = CameraMode.UNDEFINED;
            }
        } catch (Exception e) {
            throw new ActuatorException(E_CAMERA_0002.getId(), E_CAMERA_0002.getName(), e);
        }

        return mode;
    }
    public void updateMode(CameraMode mode) {
        var digitalOutput = PiGpioFactory
                .createOutputInstance(DIGITAL_OUTPUT_CAMERA_MODE, this.getCameraModeControlPin());
        if (CameraMode.NORM.equals(mode)) {
            digitalOutput.high();
        } else if (CameraMode.NIGHT.equals(mode)) {
            digitalOutput.low();
        }
    }
}
