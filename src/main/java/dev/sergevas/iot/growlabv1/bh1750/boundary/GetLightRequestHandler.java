package dev.sergevas.iot.growlabv1.bh1750.boundary;

import dev.sergevas.iot.growlabv1.shared.controller.SensorResponseBuilder;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;

import javax.json.JsonObject;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class GetLightRequestHandler implements Handler {

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        JsonObject returnObject = new SensorResponseBuilder()
                .sType(SensorType.LIGHT)
                .sData(new Bh1750Adapter().getLightIntensity())
                .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .buildJsonObject();
        res.send(returnObject);

    }
}
