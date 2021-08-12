package dev.sergevas.iot.growlabv1.camera.boundary;

import uk.co.caprica.picam.Camera;
import uk.co.caprica.picam.CameraConfiguration;

public interface IPiCamAdapter {

    public CameraConfiguration getCameraConfiguration();

    public Camera initCamera(CameraConfiguration cameraConfiguration);

    public void closeCamera();

    public byte[] takePictureWithCamRecover();
}
