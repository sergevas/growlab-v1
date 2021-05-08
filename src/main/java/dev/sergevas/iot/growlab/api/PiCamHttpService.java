package dev.sergevas.iot.growlab.api;

import dev.sergevas.iot.growlab.camera.controller.PiCamService;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import org.apache.commons.codec.binary.Base64;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.logging.Logger;

public class PiCamHttpService implements Service {

    private static final Logger LOG = Logger.getLogger(PiCamHttpService.class.getName());
    public static final JsonBuilderFactory JSON_BUILDER_FACTORY = Json.createBuilderFactory(Collections.emptyMap());

    private PiCamService piCamService;

    public PiCamHttpService(Config config) {
        piCamService = new PiCamService();
        piCamService.initPiCam();
//        config.get("propertyName").asString().orElse("defaultPropertyValue");
    }

    public PiCamService getPiCamService() {
        return piCamService;
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::getPiCamMessageHandler);
    }

    private void getPiCamMessageHandler(ServerRequest request, ServerResponse response) {
        LOG.info("Handle request... " + request);
        byte[] rawPicture = this.piCamService.takePicture();
        JsonObject returnObject = JSON_BUILDER_FACTORY.createObjectBuilder()
                .add("picture", Base64.encodeBase64String(rawPicture))
                .build();
        response.send(returnObject);
    }
}
