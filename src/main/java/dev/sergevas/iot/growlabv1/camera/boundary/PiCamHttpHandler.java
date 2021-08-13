package dev.sergevas.iot.growlabv1.camera.boundary;

import io.helidon.config.Config;
import io.helidon.webserver.*;
import org.apache.commons.codec.binary.Base64;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.logging.Logger;

public class PiCamHttpHandler implements Handler {

    private static final Logger LOG = Logger.getLogger(PiCamHttpHandler.class.getName());
    public static final JsonBuilderFactory JSON_BUILDER_FACTORY = Json.createBuilderFactory(Collections.emptyMap());
    private PiCamAdapter piCamAdapter;

    public PiCamHttpHandler(Config config) {
        this.piCamAdapter = PiCamAdapter.getInstance();
        this.piCamAdapter.installNativeLib();
        this.piCamAdapter.createCameraConfiguration(config);
    }

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        LOG.info("Handle request... " + req);
        byte[] rawPicture = this.piCamAdapter.takePictureWithCamRecover();
        JsonObject returnObject = JSON_BUILDER_FACTORY.createObjectBuilder()
                .add("picture", Base64.encodeBase64String(rawPicture))
                .build();
        res.send(returnObject);
    }
}
