package dev.sergevas.iot.growlab.camera.boundary;

import uk.co.caprica.picam.*;
import uk.co.caprica.picam.enums.AutomaticWhiteBalanceMode;
import uk.co.caprica.picam.enums.Encoding;

import static uk.co.caprica.picam.CameraConfiguration.cameraConfiguration;
import static uk.co.caprica.picam.PicamNativeLibrary.installTempLibrary;

public class PiCamControl {

    static {
        try {
            installTempLibrary();
        } catch (NativeLibraryException nle) {
            throw new PiCamException("Unable to load picam native library", nle);
        }
    }

    private static PiCamControl piCamInitializer = null;
    private Camera camera;

    private PiCamControl() {
    }

    public static synchronized PiCamControl piCamControl() {
        if (piCamInitializer == null) {
            piCamInitializer = new PiCamControl();
        }
        return piCamInitializer;
    }

    public Camera getCamera() {
        return camera;
    }

    public CameraConfiguration createCameraConfig() {
        return cameraConfiguration()
                .width(1920)
                .height(1080)
                .automaticWhiteBalance(AutomaticWhiteBalanceMode.AUTO)
                .encoding(Encoding.JPEG)
                .quality(100);
    }

    public Camera initCamera() {
        try {
            this.closeCamera();
            this.camera = new Camera(this.createCameraConfig());
        } catch (CameraException ce) {
            if (this.camera != null) this.camera.close();
            throw new PiCamException("Unable to init Camera instance", ce);
        }
        return this.camera;
    }

    public void closeCamera() {
        if (this.camera != null) this.camera.close();
    }

    public byte[] takePicture() {
        byte[] rawBytes = null;
        ByteArrayPictureCaptureHandler pictureCaptureHandler = new ByteArrayPictureCaptureHandler(1024);
        try {
            this.camera.takePicture(pictureCaptureHandler);
            rawBytes = pictureCaptureHandler.result();
        } catch (CaptureFailedException cfe) {
            throw new PiCamException("Unable to take picture", cfe);
        }
        return rawBytes;
    }
}
