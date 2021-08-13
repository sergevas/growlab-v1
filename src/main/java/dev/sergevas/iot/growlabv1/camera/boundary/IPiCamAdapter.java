package dev.sergevas.iot.growlabv1.camera.boundary;

import io.helidon.config.Config;
import uk.co.caprica.picam.Camera;
import uk.co.caprica.picam.CameraConfiguration;

public interface IPiCamAdapter {

    CameraConfiguration createCameraConfiguration(Config config);

    CameraConfiguration getPiCamCfg();

    Camera initCamera();

    void closeCamera();

    byte[] takePictureWithCamRecover();
}
