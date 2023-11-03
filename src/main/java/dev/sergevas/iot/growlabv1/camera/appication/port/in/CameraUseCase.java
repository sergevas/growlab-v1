package dev.sergevas.iot.growlabv1.camera.appication.port.in;

import dev.sergevas.iot.growlabv1.camera.domain.CameraMode;
import dev.sergevas.iot.growlabv1.camera.domain.CameraPicture;

public interface CameraUseCase {

    CameraPicture takePicture();

    CameraMode getMode();

    void updateMode(CameraMode mode);
}
