package dev.sergevas.iot.growlabv1.camera.adapter.out.camera;

import dev.sergevas.iot.growlabv1.camera.appication.port.out.CameraModeControl;
import dev.sergevas.iot.growlabv1.camera.domain.CameraMode;
import dev.sergevas.iot.growlabv1.hardware.application.port.out.HardwareException;
import dev.sergevas.iot.growlabv1.shared.application.port.out.ActuatorException;
import io.quarkiverse.jef.java.embedded.framework.linux.gpio.GpioPin;
import io.quarkiverse.jef.java.embedded.framework.runtime.gpio.GPIO;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;

import static dev.sergevas.iot.growlabv1.shared.domain.ErrorEventId.E_CAMERA_0002;
import static dev.sergevas.iot.growlabv1.shared.domain.ErrorEventId.E_CAMERA_0003;

@ApplicationScoped
public class CameraModeControlAdapter implements CameraModeControl {

    @GPIO(name = "CameraModeControlPinNumber", number = 17)
    GpioPin cameraModeControlPin;

    @PostConstruct
    public void init() {
        try {
            cameraModeControlPin.setDirection(GpioPin.Direction.OUTPUT);
        } catch (IOException e) {
            String errorMsgFormatted = "Unable to init Camera Control pin %s";
            Log.errorf(e, errorMsgFormatted, cameraModeControlPin);
            throw new HardwareException(String.format(errorMsgFormatted, cameraModeControlPin), e);
        }
    }

    public CameraMode getMode() {
        CameraMode mode;
        try {
            var state = cameraModeControlPin.read();
            if (GpioPin.State.HIGH.equals(state)) {
                mode = CameraMode.NORM;
            } else if (GpioPin.State.LOW.equals(state)) {
                mode = CameraMode.NIGHT;
            } else {
                mode = CameraMode.UNDEFINED;
            }
        } catch (IOException e) {
            Log.error(e);
            throw new ActuatorException(E_CAMERA_0002.getId(), E_CAMERA_0002.getName(), e);
        }
        return mode;
    }

    public void updateMode(CameraMode mode) {
        try {
            if (CameraMode.NORM.equals(mode)) {
                cameraModeControlPin.write(GpioPin.State.HIGH);
            } else if (CameraMode.NIGHT.equals(mode)) {
                cameraModeControlPin.write(GpioPin.State.LOW);
            }
        } catch (IOException e) {
            Log.error(e);
            throw new ActuatorException(E_CAMERA_0003.getId(), E_CAMERA_0003.getName(), e);
        }
    }
}
