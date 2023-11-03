package dev.sergevas.iot.growlabv1.camera.adapter.out.camera;

import dev.sergevas.iot.growlabv1.camera.appication.port.out.TakeCameraPicture;
import dev.sergevas.iot.growlabv1.hardware.application.port.out.HardwareException;
import dev.sergevas.iot.growlabv1.shared.application.port.out.SensorException;
import dev.sergevas.iot.growlabv1.shared.domain.SensorType;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import uk.co.caprica.picam.*;

import static dev.sergevas.iot.growlabv1.shared.domain.ErrorEventId.E_CAMERA_0001;
import static uk.co.caprica.picam.CameraConfiguration.cameraConfiguration;
import static uk.co.caprica.picam.PicamNativeLibrary.installTempLibrary;

@ApplicationScoped
public class PiCamAdapter implements TakeCameraPicture {

    @Inject
    CameraConfig camConf;
    @ConfigProperty(name = "camera.delay")
    int delay;
    private Camera camera;
    private CameraConfiguration piCamCfg;

    @PostConstruct
    public void init() {
        installNativeLib();
        createCameraConfiguration();
    }

    @PreDestroy
    public void destroy() {
        closeCamera();
    }

    public void createCameraConfiguration() {
        piCamCfg = cameraConfiguration();
        piCamCfg.width(camConf.width());
        piCamCfg.height(camConf.height());
        piCamCfg.quality(camConf.quality());
        piCamCfg.brightness(camConf.brightness());
        piCamCfg.contrast(camConf.contrast());
        piCamCfg.saturation(camConf.saturation());
        piCamCfg.sharpness(camConf.sharpness());
        piCamCfg.iso(camConf.iso());
        piCamCfg.exposureMode(camConf.exposureMode());
        piCamCfg.automaticWhiteBalance(camConf.automaticWhiteBalance());
        piCamCfg.encoding(camConf.encoding());
        camConf.stereoscopic().ifPresent(
                s -> {
                    piCamCfg.stereoscopicMode(s.stereoscopicMode(), s.decimate(), s.swapEyes());
                }
        );
        camConf.videoStabilisation().ifPresent(vs -> piCamCfg.vdieoStabilsation(vs));
        camConf.shutterSpeed().ifPresent(ss -> piCamCfg.shutterSpeed(ss));
        piCamCfg.exposureMeteringMode(camConf.exposureMeteringMode());
        camConf.exposureCompensation().ifPresent(ec -> piCamCfg.exposureCompensation(ec));
        camConf.dynamicRangeCompressionStrength().ifPresent(drcs -> piCamCfg.dynamicRangeCompressionStrength(drcs));
        camConf.automaticWhiteBalanceRedGain().ifPresent(awbrg -> piCamCfg.automaticWhiteBalanceRedGain(awbrg));
        camConf.automaticWhiteBalanceBlueGain().ifPresent(awbbg -> piCamCfg.automaticWhiteBalanceBlueGain(awbbg));
        camConf.imageEffect().ifPresent(ie -> piCamCfg.imageEffect(ie));
        camConf.mirror().ifPresent(m -> piCamCfg.mirror(m));
        piCamCfg.rotation(camConf.rotation());
        camConf.corp().ifPresent(
                c -> {
                    piCamCfg.crop(c.cropX(), c.cropY(), c.cropW(), c.cropH());
                }
        );
        camConf.colourEffect().ifPresent(
                ce -> {
                    piCamCfg.colourEffect(ce.enable(), ce.u(), ce.v());
                }
        );
        camConf.captureTimeout().ifPresent(ct -> piCamCfg.captureTimeout(ct));
    }


    @Override
    public byte[] takePicture() {
        return this.takePictureWithCamRecover();
    }

    public void installNativeLib() {
        try {
            installTempLibrary();
        } catch (NativeLibraryException nle) {
            Log.error(nle);
            throw new HardwareException("Unable to load picam native library", nle);
        }
    }

    public Camera initCamera() {
        try {
            this.closeCamera();
            this.camera = new Camera(this.piCamCfg);
        } catch (CameraException ce) {
            Log.error(ce);
            if (this.camera != null) this.camera.close();
            throw new HardwareException("Unable to init Camera instance", ce);
        }
        return this.camera;
    }

    public void closeCamera() {
        if (this.camera != null) {
            this.camera.close();
        }
    }

    private synchronized byte[] takePictureWithCamRecover() {
        byte[] rawBytes;
        try {
            rawBytes = this.takePictureDirect();
        } catch (CaptureFailedException cfe) {
            Log.warnf(cfe, "Trying to recover...");
            try {
                this.initCamera();
                rawBytes = this.takePictureDirect();
            } catch (Exception e) {
                Log.error(e);
                throw new SensorException(E_CAMERA_0001.getId(), SensorType.CAMERA, E_CAMERA_0001.getName(), e);
            }
        } catch (Throwable t) {
            Log.error(t);
            throw new SensorException(E_CAMERA_0001.getId(), SensorType.CAMERA, E_CAMERA_0001.getName(), t);
        }
        return rawBytes;
    }

    private byte[] takePictureDirect() throws CaptureFailedException {
        byte[] rawBytes;
        ByteArrayPictureCaptureHandler pictureCaptureHandler = new ByteArrayPictureCaptureHandler(1024);
        if (this.camera == null) {
            this.camera = this.initCamera();
        }
        this.camera.takePicture(pictureCaptureHandler, this.delay);
        rawBytes = pictureCaptureHandler.result();
        return rawBytes;
    }
}
