package dev.sergevas.iot.growlabv1;

import dev.sergevas.iot.growlabv1.bh1750.boundary.Bh1750Adapter;
import io.helidon.webserver.Routing;
import io.helidon.webserver.Service;
import java.util.logging.Logger;

public class SensorsHttpervice implements Service {
    private static final Logger LOG = Logger.getLogger(SensorsHttpervice.class.getName());

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/light", (request, response) -> {
            LOG.info("Handle request... " + request);
            response.send(new Bh1750Adapter().getLightIntensity());
        });
    }
}
