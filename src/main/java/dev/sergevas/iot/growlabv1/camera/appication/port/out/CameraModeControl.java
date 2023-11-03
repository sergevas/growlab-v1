package dev.sergevas.iot.growlabv1.camera.appication.port.out;

import dev.sergevas.iot.growlabv1.camera.domain.CameraMode;

public interface CameraModeControl {
    CameraMode getMode();

    void updateMode(CameraMode mode);
}
