package dev.sergevas.iot.growlabv1.bh1750.boundary;

import dev.sergevas.iot.growlabv1.bh1750.boundary.Bh1750Adapter;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.Json;
import java.util.Collections;


public class GetLightRequestHandler implements Handler {

    @Override
    public void accept(ServerRequest req, ServerResponse res) {

        response.send(returnObject);
        res.send(new Bh1750Adapter().getLightIntensity());
    }
}
