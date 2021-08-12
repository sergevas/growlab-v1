package dev.sergevas.iot.growlabv1.camera.boundary;

import dev.sergevas.iot.growlabv1.camera.exception.PiCamException;
import uk.co.caprica.picam.*;

import java.util.Optional;

import static uk.co.caprica.picam.CameraConfiguration.cameraConfiguration;
import static uk.co.caprica.picam.PicamNativeLibrary.installTempLibrary;

public class PiCamAdapter implements IPiCamAdapter {

    private static PiCamAdapter piCamAdapter;
    private Camera camera;
    private CameraConfiguration cameraConfiguration;

    private PiCamAdapter() {
    }

    public static synchronized PiCamAdapter getInstance() {
        if (piCamAdapter == null) {
            piCamAdapter = new PiCamAdapter();
        }
        return piCamAdapter;
    }

    public CameraConfiguration getCameraConfiguration() {
        return Optional.ofNullable(cameraConfiguration).orElseGet(() -> {
            cameraConfiguration = cameraConfiguration();
            return cameraConfiguration;
        });
    }

//    public CameraConfiguration createDefaultCameraConfig() {
//        return cameraConfiguration()
//                .width(1920)
//                .height(1080)
//                .automaticWhiteBalance(AutomaticWhiteBalanceMode.AUTO)
//                .encoding(Encoding.JPEG)
//                .quality(100);
//    }

    public void installNativeLib() {
        try {
            installTempLibrary();
        } catch (NativeLibraryException nle) {
            throw new PiCamException("Unable to load picam native library", nle);
        }
    }

    public Camera initCamera(CameraConfiguration cameraConfiguration) {
        try {
            this.closeCamera();
            this.camera = new Camera(cameraConfiguration);
        } catch (CameraException ce) {
            if (this.camera != null) this.camera.close();
            throw new PiCamException("Unable to init Camera instance", ce);
        }
        return this.camera;
    }

//    public Camera initCamera() {
//        return this.initCamera(this.createDefaultCameraConfig());
//    }

    public void closeCamera() {
        if (this.camera != null) {
            this.camera.close();
        }
    }

    public synchronized byte[] takePictureWithCamRecover() {
        byte[] rawBytes = null;
        try {
            rawBytes = this.takePicture();
        } catch (CaptureFailedException cfe1) {
            this.initCamera(this.cameraConfiguration);
            try {
                rawBytes = this.takePicture();
            } catch (CaptureFailedException cfe2) {
                throw new PiCamException("Unable to take a picture", cfe2);
            }
        }
        return rawBytes;
    }

    private byte[] takePicture() throws CaptureFailedException {
        byte[] rawBytes = null;
        ByteArrayPictureCaptureHandler pictureCaptureHandler = new ByteArrayPictureCaptureHandler(1024);
        this.camera.takePicture(pictureCaptureHandler);
        rawBytes = pictureCaptureHandler.result();
        return rawBytes;
    }
}
