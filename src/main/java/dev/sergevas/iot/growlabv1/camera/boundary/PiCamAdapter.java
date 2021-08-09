package dev.sergevas.iot.growlabv1.camera.boundary;

import dev.sergevas.iot.growlabv1.camera.exception.PiCamException;
import uk.co.caprica.picam.*;
import uk.co.caprica.picam.enums.AutomaticWhiteBalanceMode;
import uk.co.caprica.picam.enums.Encoding;

import java.util.Optional;

import static uk.co.caprica.picam.CameraConfiguration.cameraConfiguration;
import static uk.co.caprica.picam.PicamNativeLibrary.installTempLibrary;

public class PiCamAdapter implements IPiCamAdapter {

    private static PiCamAdapter piCamInitializer = null;
    private Camera camera;
    private CameraConfiguration cameraConfiguration;

    private PiCamAdapter() {
    }

    public static synchronized PiCamAdapter getInstance() {
        if (piCamInitializer == null) {
            piCamInitializer = new PiCamAdapter();
        }
        return piCamInitializer;
    }

    public CameraConfiguration getCameraConfiguration() {
        return Optional.ofNullable(cameraConfiguration).orElseGet(() -> {
            cameraConfiguration = cameraConfiguration();
            return cameraConfiguration;
        });
    }

    public CameraConfiguration createDefaultCameraConfig() {
        return cameraConfiguration()
                .width(1920)
                .height(1080)
                .automaticWhiteBalance(AutomaticWhiteBalanceMode.AUTO)
                .encoding(Encoding.JPEG)
                .quality(100);
    }

    public Camera initCamera(CameraConfiguration cameraConfiguration) {
        try {
            installTempLibrary();
        } catch (NativeLibraryException nle) {
            throw new PiCamException("Unable to load picam native library", nle);
        }

        try {
            this.closeCamera();
            this.camera = new Camera(cameraConfiguration);
        } catch (CameraException ce) {
            if (this.camera != null) this.camera.close();
            throw new PiCamException("Unable to init Camera instance", ce);
        }
        return this.camera;
    }

    public Camera initCamera() {
        return this.initCamera(this.createDefaultCameraConfig());
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
            throw new PiCamException("Unable to take a picture", cfe);
        }
        return rawBytes;
    }
}
