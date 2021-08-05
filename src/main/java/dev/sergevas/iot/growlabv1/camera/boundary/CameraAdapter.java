package dev.sergevas.iot.growlabv1.camera.boundary;

import dev.sergevas.iot.growlabv1.camera.model.CameraMode;
import dev.sergevas.iot.growlabv1.hardware.boundary.PiGpioFactory;

public class CameraAdapter {

    private static final int CAMERA_MODE_CONTROL_PIN = 17;
    private static final String DIGITAL_OUTPUT_CAMERA_MODE = "digital-output-camera-control";

    public CameraMode getMode() {
        CameraMode mode = null;
        var digitalOutput = PiGpioFactory
                .createOutputInstance(DIGITAL_OUTPUT_CAMERA_MODE, CAMERA_MODE_CONTROL_PIN);
        if (digitalOutput.isHigh()) {
            mode = CameraMode.NORM;
        } else if (digitalOutput.isLow()) {
            mode = CameraMode.NIGHT;
        } else {
            mode = CameraMode.UNDEFINED;
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
