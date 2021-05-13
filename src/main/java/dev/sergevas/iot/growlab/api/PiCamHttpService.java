package dev.sergevas.iot.growlab.api;

import dev.sergevas.iot.growlab.camera.boundary.PiCamControl;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import org.apache.commons.codec.binary.Base64;
import uk.co.caprica.picam.enums.AutomaticWhiteBalanceMode;
import uk.co.caprica.picam.enums.Encoding;
import uk.co.caprica.picam.enums.ExposureMode;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.logging.Logger;

public class PiCamHttpService implements Service {

    private static final Logger LOG = Logger.getLogger(PiCamHttpService.class.getName());
    public static final JsonBuilderFactory JSON_BUILDER_FACTORY = Json.createBuilderFactory(Collections.emptyMap());
    private PiCamControl piCamControl;

    public PiCamHttpService(Config config) {
        this.piCamControl = PiCamControl.piCamControl();
        this.piCamControl.initCamera(this.piCamControl
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

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::getPiCamMessageHandler);
    }

    private void getPiCamMessageHandler(ServerRequest request, ServerResponse response) {
        LOG.info("Handle request... " + request);
        byte[] rawPicture = this.piCamControl.takePicture();
        JsonObject returnObject = JSON_BUILDER_FACTORY.createObjectBuilder()
                .add("picture", Base64.encodeBase64String(rawPicture))
                .build();
        response.send(returnObject);
    }
}
