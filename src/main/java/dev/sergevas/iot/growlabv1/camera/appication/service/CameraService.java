package dev.sergevas.iot.growlabv1.camera.appication.service;

import dev.sergevas.iot.growlabv1.camera.appication.port.in.CameraUseCase;
import dev.sergevas.iot.growlabv1.camera.appication.port.out.CameraModeControl;
import dev.sergevas.iot.growlabv1.camera.appication.port.out.TakeCameraPicture;
import dev.sergevas.iot.growlabv1.camera.domain.CameraMode;
import dev.sergevas.iot.growlabv1.camera.domain.CameraPicture;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
        return new CameraMode(cameraModeControl.getMode(), OffsetDateTime.now(ZoneOffset.UTC));

    }

    @Override
    public void updateMode(CameraMode cameraMode) {
        cameraModeControl.updateMode(cameraMode.mode());
    }
}
