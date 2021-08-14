package dev.sergevas.iot.growlabv1.camera.boundary;

import dev.sergevas.iot.growlabv1.hardware.boundary.HardwareException;
import dev.sergevas.iot.growlabv1.shared.controller.ExceptionUtils;
import dev.sergevas.iot.growlabv1.shared.exception.ActuatorException;
import io.helidon.config.Config;
import uk.co.caprica.picam.*;
import uk.co.caprica.picam.enums.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_CAMERA_0001;
import static uk.co.caprica.picam.CameraConfiguration.cameraConfiguration;
import static uk.co.caprica.picam.PicamNativeLibrary.installTempLibrary;

public class PiCamAdapter {

    private static final String CLASS_NAME = PiCamAdapter.class.getName();
    private static final Logger LOG = Logger.getLogger(PiCamAdapter.class.getName());

    private static PiCamAdapter piCamAdapter;
    private Camera camera;
    private CameraConfiguration piCamCfg;

    private PiCamAdapter() {
    }

    public static synchronized PiCamAdapter getInstance() {
        LOG.info(CLASS_NAME + " Get instance...");
        if (piCamAdapter == null) {
            piCamAdapter = new PiCamAdapter();
        }
        return piCamAdapter;
    }

    public CameraConfiguration createCameraConfiguration(Config config) {
        LOG.info(CLASS_NAME + " Create camera configuration...");
        Config hCamCfg = config.get("camera");
        piCamCfg = cameraConfiguration();

        hCamCfg.get("width").ifExists(p -> piCamCfg.width(p.asInt().get()));
        hCamCfg.get("height").ifExists(p -> piCamCfg.height(p.asInt().get()));
        hCamCfg.get("quality").ifExists(p -> piCamCfg.quality(p.asInt().get()));
        hCamCfg.get("brightness").ifExists(p -> piCamCfg.brightness(p.asInt().get()));
        hCamCfg.get("contrast").ifExists(p -> piCamCfg.contrast(p.asInt().get()));
        hCamCfg.get("saturation").ifExists(p -> piCamCfg.saturation(p.asInt().get()));
        hCamCfg.get("sharpness").ifExists(p -> piCamCfg.sharpness(p.asInt().get()));
        hCamCfg.get("iso").ifExists(p -> piCamCfg.iso(p.asInt().get()));
        hCamCfg.get("exposureMode").ifExists(p -> piCamCfg.exposureMode(ExposureMode.valueOf(p.asString().get())));
        hCamCfg.get("automaticWhiteBalance").ifExists(p -> piCamCfg.automaticWhiteBalance(AutomaticWhiteBalanceMode.valueOf(p.asString().get())));
        hCamCfg.get("encoding").ifExists(p -> piCamCfg.encoding(Encoding.valueOf(p.asString().get())));
        hCamCfg.get("stereoscopic")
                .ifExists(p -> piCamCfg.stereoscopicMode(StereoscopicMode.valueOf(p.get("stereoscopicMode").asString().get()),
                        p.get("decimate").asBoolean().orElse(Boolean.FALSE),
                        p.get("swapEyes").asBoolean().orElse(Boolean.FALSE)));
        hCamCfg.get("videoStabilisation").ifExists(p -> piCamCfg.vdieoStabilsation(p.asBoolean().get()));
        hCamCfg.get("shutterSpeed").ifExists(p -> piCamCfg.shutterSpeed(p.asInt().get()));
        hCamCfg.get("exposureMeteringMode").ifExists(p -> piCamCfg.exposureMeteringMode(ExposureMeteringMode.valueOf(p.asString().get())));
        hCamCfg.get("exposureCompensation").ifExists(p -> piCamCfg.exposureCompensation(p.asInt().get()));
        hCamCfg.get("dynamicRangeCompressionStrength").ifExists(p -> piCamCfg.dynamicRangeCompressionStrength(DynamicRangeCompressionStrength.valueOf(p.asString().get())));
        hCamCfg.get("automaticWhiteBalanceRedGain").ifExists(p -> piCamCfg.automaticWhiteBalanceRedGain(p.asDouble().get().floatValue()));
        hCamCfg.get("automaticWhiteBalanceBlueGain").ifExists(p -> piCamCfg.automaticWhiteBalanceBlueGain(p.asDouble().get().floatValue()));
        hCamCfg.get("imageEffect").ifExists(p -> piCamCfg.imageEffect(ImageEffect.valueOf(p.asString().get())));
        hCamCfg.get("mirror").ifExists(p -> piCamCfg.mirror(Mirror.valueOf(p.asString().get())));
        hCamCfg.get("rotation").ifExists(p -> piCamCfg.rotation(p.asInt().get()));
        hCamCfg.get("corp")
                .ifExists(p -> piCamCfg.crop(p.get("cropX").asDouble().get(),
                        p.get("cropY").asDouble().get(),
                        p.get("cropW").asDouble().get(),
                        p.get("cropH").asDouble().get()));
        hCamCfg.get("colourEffect")
                .ifExists(p -> piCamCfg.colourEffect(p.get("enable").asBoolean().orElse(Boolean.FALSE),
                        p.get("u").asInt().get(),
                        p.get("v").asInt().get()));
        hCamCfg.get("captureTimeout").ifExists(p -> piCamCfg.captureTimeout(p.asInt().get()));
        return piCamCfg;
    }

    public void installNativeLib() {
        LOG.info(CLASS_NAME + " Install native lib...");
        try {
            installTempLibrary();
        } catch (NativeLibraryException nle) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(nle));
            throw new HardwareException("Unable to load picam native library", nle);
        }
    }

    public Camera initCamera() {
        LOG.info(CLASS_NAME + " Init camera...");
        try {
            this.closeCamera();
            this.camera = new Camera(this.piCamCfg);
        } catch (CameraException ce) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(ce));
            if (this.camera != null) this.camera.close();
            throw new HardwareException("Unable to init Camera instance", ce);
        }
        return this.camera;
    }

    public void closeCamera() {
        LOG.info(CLASS_NAME + " Close camera...");
        if (this.camera != null) {
            this.camera.close();
        }
    }

    public synchronized byte[] takePictureWithCamRecover() {
        LOG.info(CLASS_NAME + " Take picture with recover...");
        byte[] rawBytes = null;
        try {
            rawBytes = this.takePicture();
        } catch (CaptureFailedException cfe) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(cfe));
            LOG.info("Trying to recover...");
            try {
                this.initCamera();
                rawBytes = this.takePicture();
            } catch (Exception e) {
                LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
                throw new ActuatorException(E_CAMERA_0001.getId(), E_CAMERA_0001.getName(), e);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
            throw new ActuatorException(E_CAMERA_0001.getId(), E_CAMERA_0001.getName(), e);
        }
        return rawBytes;
    }

    private byte[] takePicture() throws CaptureFailedException {
        byte[] rawBytes = null;
        ByteArrayPictureCaptureHandler pictureCaptureHandler = new ByteArrayPictureCaptureHandler(1024);
        if (this.camera == null) {
            this.camera = this.initCamera();
        }
        this.camera.takePicture(pictureCaptureHandler);
        rawBytes = pictureCaptureHandler.result();
        return rawBytes;
    }
}
