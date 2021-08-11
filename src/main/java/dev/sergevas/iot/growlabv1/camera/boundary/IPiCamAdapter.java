package dev.sergevas.iot.growlabv1.camera.boundary;

import uk.co.caprica.picam.Camera;
import uk.co.caprica.picam.CameraConfiguration;

public interface IPiCamAdapter {

    public CameraConfiguration getCameraConfiguration();

//    public CameraConfiguration createDefaultCameraConfig();

    public Camera initCamera(CameraConfiguration cameraConfiguration);

    public Camera initCamera();

    public void closeCamera();

    public byte[] takePicture();
}
