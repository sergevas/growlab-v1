package dev.sergevas.iot.growlabv1.camera.boundary;

import io.helidon.config.Config;
import io.helidon.webserver.*;
import org.apache.commons.codec.binary.Base64;
import uk.co.caprica.picam.enums.AutomaticWhiteBalanceMode;
import uk.co.caprica.picam.enums.Encoding;
import uk.co.caprica.picam.enums.ExposureMode;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.logging.Logger;

public class PiCamHttpHandler implements Handler {

    private static final Logger LOG = Logger.getLogger(PiCamHttpHandler.class.getName());
    public static final JsonBuilderFactory JSON_BUILDER_FACTORY = Json.createBuilderFactory(Collections.emptyMap());
    private PiCamAdapter piCamAdapter;

    /*
    *
    * width: 1920
  height: 1080
  quality: 100
  brightness: 50
  contrast: 0
  saturation: 0
  sharpness: 0
  iso: 0
  exposureMode: AUTO
  automaticWhiteBalance: AUTO
  encoding: JPEG
    * */

    public PiCamHttpHandler(Config config) {
        this.piCamAdapter = PiCamAdapter.getInstance();
        this.piCamAdapter.initCamera(this.piCamAdapter
                .getCameraConfiguration()
                .width(config.get("camera.width").asInt().orElse(1920))
                .height(config.get("camera.height").asInt().orElse(1080))
                .quality(config.get("camera.quality").asInt().orElse(100))
                .brightness(config.get("camera.brightness").asInt().orElse(50))
                .contrast(config.get("camera.contrast").asInt().orElse(0))
                .saturation(config.get("camera.saturation").asInt().orElse(0))
                .sharpness(config.get("camera.sharpness").asInt().orElse(0))
                .iso(config.get("camera.iso").asInt().orElse(0))
                .exposureMode(config.get("camera.exposureMode")
                        .asString()
                        .as(ExposureMode::valueOf)
                        .orElse(ExposureMode.AUTO))
                .automaticWhiteBalance(config.get("camera.automaticWhiteBalance")
                        .asString()
                        .as(AutomaticWhiteBalanceMode::valueOf)
                        .orElse(AutomaticWhiteBalanceMode.AUTO))
                .encoding(config.get("camera.encoding")
                        .asString()
                        .as(Encoding::valueOf)
                        .orElse(Encoding.JPEG)));
    }

    /*
    * #  cameraNumber: 0
#  customSensorConfig: 0
#  stereoscopicMode: NONE
#  decimate: false
#  swapEyes: false
#  videoStabilisation: false
#  shutterSpeed: 0
#  exposureMeteringMode: AVERAGE
#  exposureCompensation: 0
#  dynamicRangeCompressionStrength: OFF
#  automaticWhiteBalanceMode: OFF
#  automaticWhiteBalanceRedGain: 0.0
#  automaticWhiteBalanceBlueGain: 0.0
#  imageEffect: NONE
#  mirror: NONE
#  rotation: 0
#  cropX: 0.0
#  cropY: 0.0
#  cropW: 0.0
#  cropH: 0.0
#  colourEffect: false
#  u: 0
#  v: 0
#  captureTimeout: 0
    *
    * */

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        LOG.info("Handle request... " + req);
        byte[] rawPicture = this.piCamAdapter.takePicture();
        JsonObject returnObject = JSON_BUILDER_FACTORY.createObjectBuilder()
                .add("picture", Base64.encodeBase64String(rawPicture))
                .build();
        res.send(returnObject);

    }
}
