package dev.sergevas.iot.growlabv1.camera.adapter.out.camera;

import dev.sergevas.iot.growlabv1.camera.appication.port.out.CameraModeControl;
import dev.sergevas.iot.growlabv1.camera.domain.CameraMode.Mode;
import dev.sergevas.iot.growlabv1.shared.application.port.out.ActuatorException;
import dev.sergevas.iot.growlabv1.shared.application.port.out.HardwareException;
import io.quarkiverse.jef.java.embedded.framework.linux.gpio.GpioPin;
import io.quarkiverse.jef.java.embedded.framework.runtime.gpio.GPIO;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;

import static dev.sergevas.iot.growlabv1.shared.domain.ErrorEventId.E_CAMERA_0003;

@ApplicationScoped
public class CameraModeControlAdapter implements CameraModeControl {

    @GPIO(name = "<default>", number = 17)
    GpioPin cameraModeControlPin;

    private Mode currentMode;

    @PostConstruct
    public void init() {
        try {
            cameraModeControlPin.setDirection(GpioPin.Direction.OUTPUT);
            currentMode = Mode.NORM;
            updateMode(currentMode);
        } catch (IOException e) {
            String errorMsgFormatted = "Unable to init Camera Control pin %s";
            Log.errorf(e, errorMsgFormatted, cameraModeControlPin);
            throw new HardwareException(String.format(errorMsgFormatted, cameraModeControlPin), e);
        }
    }

    public Mode getMode() {
        return currentMode;
    }

    public void updateMode(Mode mode) {
        try {
            if (Mode.NORM.equals(mode)) {
                cameraModeControlPin.write(GpioPin.State.HIGH);
            } else if (Mode.NIGHT.equals(mode)) {
                cameraModeControlPin.write(GpioPin.State.LOW);
            }
            currentMode = mode;
        } catch (IOException e) {
            Log.error(e);
            throw new ActuatorException(E_CAMERA_0003.getId(), E_CAMERA_0003.getName(), e);
        }
    }
}
