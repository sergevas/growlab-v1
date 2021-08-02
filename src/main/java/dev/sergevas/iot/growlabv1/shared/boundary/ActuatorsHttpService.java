package dev.sergevas.iot.growlabv1.shared.boundary;

import dev.sergevas.iot.growlabv1.camera.boundary.GetCameraModeRequestHandler;
import dev.sergevas.iot.growlabv1.camera.boundary.UpdateCameraModeRequestHandler;
import io.helidon.webserver.Routing;
import io.helidon.webserver.Service;

import java.util.logging.Logger;

public class ActuatorsHttpService implements Service {
    private static final Logger LOG = Logger.getLogger(ActuatorsHttpService.class.getName());

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/camera/mode", new GetCameraModeRequestHandler());
        rules.put("/camera/mode", new UpdateCameraModeRequestHandler());
    }
}
