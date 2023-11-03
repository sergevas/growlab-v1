package dev.sergevas.iot.growlabv1.camera.appication.service;

import dev.sergevas.iot.growlabv1.camera.appication.port.in.CameraUseCase;
import dev.sergevas.iot.growlabv1.camera.appication.port.out.CameraModeControl;
import dev.sergevas.iot.growlabv1.camera.appication.port.out.TakeCameraPicture;
import dev.sergevas.iot.growlabv1.camera.domain.CameraMode;
import dev.sergevas.iot.growlabv1.camera.domain.CameraPicture;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CameraService implements CameraUseCase {

    @Inject
    TakeCameraPicture takeCameraPicture;
    @Inject
    CameraModeControl cameraModeControl;

    @Override
    public CameraPicture takePicture() {
        return new CameraPicture(takeCameraPicture.takePicture());
    }

    @Override
    public CameraMode getMode() {
        return cameraModeControl.getMode();
    }

    @Override
    public void updateMode(CameraMode mode) {
        cameraModeControl.updateMode(mode);
    }
}
