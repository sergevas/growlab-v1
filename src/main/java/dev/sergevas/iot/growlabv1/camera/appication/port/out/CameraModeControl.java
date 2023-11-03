package dev.sergevas.iot.growlabv1.camera.appication.port.out;

import dev.sergevas.iot.growlabv1.camera.domain.CameraMode.Mode;

public interface CameraModeControl {
    Mode getMode();
    void updateMode(Mode mode);
}
