package dev.sergevas.iot.growlabv1.camera.boundary;

import dev.sergevas.iot.growlabv1.camera.model.CameraMode;
import dev.sergevas.iot.growlabv1.shared.exception.ActuatorException;
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

import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_CAMERA_0003;

public class UpdateCameraModeRequestHandler implements Handler {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());
    private static final Logger LOG = Logger.getLogger(UpdateCameraModeRequestHandler.class.getName());

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        req.content()
                .as(JsonObject.class)
                .thenAccept(jo -> updateCameraMode(jo, res))
                .exceptionally(ex -> processUpdateErrors(ex, req, res));
    }

    private void updateCameraMode(JsonObject jsonObject, ServerResponse res) {
        String modeStr = jsonObject.getString("mode");
        LOG.info("Camera mode: " + modeStr);
        CameraMode cameraMode = CameraMode.valueOf(modeStr);
        CameraModeControlAdapter.getInstance().updateMode(cameraMode);
        res.status(Http.Status.OK_200).send();
    }

    private static <T> T processUpdateErrors(Throwable e, ServerRequest req, ServerResponse res) {
        if (e.getCause() instanceof JsonException) {
            LOG.log(Level.SEVERE, "Invalid JSON", e);
            JsonObject jsonErrorObject = JSON.createObjectBuilder()
                    .add("error", "Invalid Request content")
                    .build();
            res.status(Http.Status.BAD_REQUEST_400).send(jsonErrorObject);
        }  else {
            throw new ActuatorException(E_CAMERA_0003.getId(), E_CAMERA_0003.getName(), e);
        }
        return null;
    }
}
