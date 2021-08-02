package dev.sergevas.iot.growlabv1.camera.boundary;

import dev.sergevas.iot.growlabv1.bh1750.boundary.Bh1750Adapter;
import dev.sergevas.iot.growlabv1.shared.controller.SensorResponseBuilder;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

import javax.json.JsonObject;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class UpdateCameraModeRequestHandler implements Handler {

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
//        req.content().as(JsonObject.class)
//                .thenAccept(jo -> updateGreetingFromJson(jo, response))
//                .exceptionally(ex -> processErrors(ex, request, response));
//        JsonObject returnObject = new SensorResponseBuilder()
//                .sType(SensorType.LIGHT)
//                .sData(new Bh1750Adapter().getLightIntensity())
//                .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
//                .buildJsonObject();
//        res.send(returnObject);

    }
}
