package dev.sergevas.iot.growlabv1.shared.boundary;

import dev.sergevas.iot.growlabv1.bh1750.boundary.GetLightRequestHandler;
import dev.sergevas.iot.growlabv1.bme280.boundary.GetTHPRequestHandler;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.Service;
import java.util.logging.Logger;

public class SensorsHttpService implements Service {

    private static final Logger LOG = Logger.getLogger(SensorsHttpService.class.getName());

    private Config config;

    public SensorsHttpService(Config config) {
        super();
        this.config = config;
    }

    public SensorsHttpService() {
        super();
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/light", new GetLightRequestHandler(this.config));
        rules.get("/thp", new GetTHPRequestHandler(this.config));
    }
}
