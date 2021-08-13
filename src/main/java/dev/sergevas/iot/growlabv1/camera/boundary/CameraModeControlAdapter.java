package dev.sergevas.iot.growlabv1.camera.boundary;

import dev.sergevas.iot.growlabv1.camera.model.CameraMode;
import dev.sergevas.iot.growlabv1.hardware.boundary.PiGpioFactory;
import dev.sergevas.iot.growlabv1.shared.exception.ActuatorException;

import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_CAMERA_0002;

public class CameraModeControlAdapter {

    private static final int CAMERA_MODE_CONTROL_PIN = 17;
    private static final String DIGITAL_OUTPUT_CAMERA_MODE = "digital-output-camera-control";

    private static CameraModeControlAdapter instance;

    public static CameraModeControlAdapter getInstance() {
        if (instance == null) {
            instance = new CameraModeControlAdapter();
        }
        return instance;
    }

    public CameraMode getMode() {
        CameraMode mode = null;
        try {
            var digitalOutput = PiGpioFactory
                    .createOutputInstance(DIGITAL_OUTPUT_CAMERA_MODE, CAMERA_MODE_CONTROL_PIN);
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
                .createOutputInstance(DIGITAL_OUTPUT_CAMERA_MODE, CAMERA_MODE_CONTROL_PIN);
        if (CameraMode.NORM.equals(mode)) {
            digitalOutput.high();
        } else if (CameraMode.NIGHT.equals(mode)) {
            digitalOutput.low();
        }
    }
}
