package dev.sergevas.iot.growlabv1.camera.adapter.out.camera;

import io.smallrye.config.ConfigMapping;
import uk.co.caprica.picam.enums.*;

import java.util.Optional;

@ConfigMapping(prefix = "camera", namingStrategy = ConfigMapping.NamingStrategy.VERBATIM)
public interface CameraConfig {
    int width();

    int height();

    int quality();

    int brightness();

    int contrast();

    int saturation();

    int sharpness();

    int iso();

    ExposureMode exposureMode();

    AutomaticWhiteBalanceMode automaticWhiteBalance();

    Encoding encoding();

    Optional<Stereoscopic> stereoscopic();

    interface Stereoscopic {
        StereoscopicMode stereoscopicMode();

        Boolean decimate();

        Boolean swapEyes();
    }

    Optional<Boolean> videoStabilisation();

    Optional<Integer> shutterSpeed();

    ExposureMeteringMode exposureMeteringMode();

    Optional<Integer> exposureCompensation();

    Optional<DynamicRangeCompressionStrength> dynamicRangeCompressionStrength();

    Optional<Float> automaticWhiteBalanceRedGain();

    Optional<Float> automaticWhiteBalanceBlueGain();

    Optional<ImageEffect> imageEffect();

    Optional<Mirror> mirror();

    Integer rotation();

    Optional<Corp> corp();

    interface Corp {
        double cropX();

        double cropY();

        double cropW();

        double cropH();
    }

    Optional<ColourEffect> colourEffect();

    interface ColourEffect {
        boolean enable();

        int u();

        int v();

    }

    Optional<Integer> captureTimeout();

    Integer delay();
}
