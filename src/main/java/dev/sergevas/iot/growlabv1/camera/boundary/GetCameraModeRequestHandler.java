package dev.sergevas.iot.growlabv1.camera.boundary;

import dev.sergevas.iot.growlabv1.camera.controller.CameraModeBuilder;
import dev.sergevas.iot.growlabv1.shared.controller.HelidonConfigHandler;
import io.helidon.config.Config;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

import javax.json.JsonObject;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class GetCameraModeRequestHandler implements Handler {

    private CameraModeControlAdapter cameraModeControlAdapter;

    public GetCameraModeRequestHandler(Config config) {
        this.cameraModeControlAdapter = CameraModeControlAdapter
                .create(HelidonConfigHandler.getConfigMap(config, "camera"));
    }

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        JsonObject returnObject = new CameraModeBuilder()
                .mode(this.cameraModeControlAdapter.getMode())
                .modeTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .buildJsonObject();
        res.send(returnObject);

    }
}
