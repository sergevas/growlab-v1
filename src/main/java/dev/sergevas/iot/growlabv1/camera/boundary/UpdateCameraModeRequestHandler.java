package dev.sergevas.iot.growlabv1.camera.boundary;

import dev.sergevas.iot.growlabv1.camera.model.CameraMode;
import io.helidon.common.http.Http;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonException;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UpdateCameraModeRequestHandler implements Handler {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());
    private static final Logger LOG = Logger.getLogger(UpdateCameraModeRequestHandler.class.getName());

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        req.content()
                .as(JsonObject.class)
                .thenAccept(jo -> updateCameraMode(jo, res))
                .exceptionally(ex -> processErrors(ex, req, res));
    }

    private void updateCameraMode(JsonObject jsonObject, ServerResponse res) {
        String modeStr = jsonObject.getString("mode");
        LOG.info("Camera mode: " + modeStr);
        CameraMode cameraMode = CameraMode.valueOf(modeStr);
        CameraAdapter.getInstance().updateMode(cameraMode);
        res.status(Http.Status.OK_200).send();
    }

    private static <T> T processErrors(Throwable ex, ServerRequest req, ServerResponse res) {
        if (ex.getCause() instanceof JsonException){
            LOG.log(Level.FINE, "Invalid JSON", ex);
            JsonObject jsonErrorObject = JSON.createObjectBuilder()
                    .add("error", "Invalid JSON")
                    .build();
            res.status(Http.Status.BAD_REQUEST_400).send(jsonErrorObject);
        }  else {
            LOG.log(Level.FINE, "Internal error", ex);
            JsonObject jsonErrorObject = JSON.createObjectBuilder()
                    .add("error", "Internal error")
                    .build();
            res.status(Http.Status.INTERNAL_SERVER_ERROR_500).send(jsonErrorObject);
        }
        return null;
    }
}
