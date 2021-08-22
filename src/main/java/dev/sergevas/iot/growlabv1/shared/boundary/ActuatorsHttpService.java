package dev.sergevas.iot.growlabv1.shared.boundary;

import dev.sergevas.iot.growlabv1.camera.boundary.GetCameraModeRequestHandler;
import dev.sergevas.iot.growlabv1.camera.boundary.PiCamHttpHandler;
import dev.sergevas.iot.growlabv1.camera.boundary.UpdateCameraModeRequestHandler;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.Service;

import java.util.logging.Logger;

public class ActuatorsHttpService implements Service {

    private Config config;

    public ActuatorsHttpService() {
        super();
    }

    public ActuatorsHttpService(Config config) {
        super();
        this.config = config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/camera/image", new PiCamHttpHandler(this.config));
        rules.get("/camera/mode", new GetCameraModeRequestHandler(this.config));
        rules.put("/camera/mode", new UpdateCameraModeRequestHandler(this.config));
    }
}
