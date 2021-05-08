package dev.sergevas.iot.growlab.camera.controller;

import static dev.sergevas.iot.growlab.camera.boundary.PiCamControl.piCamControl;

public class PiCamService {

    public PiCamService() {
    }

    public void initPiCam() {
        piCamControl().initCamera();
    }

    public void closePiCam() {
        piCamControl().closeCamera();
    }

    public byte[] takePicture() {
        byte[] rawPicture = null;
        rawPicture = piCamControl().takePicture();
        return rawPicture;
    }
}
