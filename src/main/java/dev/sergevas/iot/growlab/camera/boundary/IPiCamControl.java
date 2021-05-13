package dev.sergevas.iot.growlab.camera.boundary;

import uk.co.caprica.picam.Camera;
import uk.co.caprica.picam.CameraConfiguration;

public interface IPiCamControl {

    public CameraConfiguration getCameraConfiguration();

    public CameraConfiguration createDefaultCameraConfig();

    public Camera initCamera(CameraConfiguration cameraConfiguration);

    public Camera initCamera();

    public void closeCamera();

    public byte[] takePicture();
}
