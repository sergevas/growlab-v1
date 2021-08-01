package dev.sergevas.iot.growlabv1.shared.boundary;

import dev.sergevas.iot.growlabv1.shared.exception.SensorException;
import io.helidon.common.http.Http;
import io.helidon.webserver.ErrorHandler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

public class SensorsErrorHandler implements ErrorHandler<SensorException> {
    @Override
    public void accept(ServerRequest req, ServerResponse res, SensorException ex) {
        res.status(Http.Status.INTERNAL_SERVER_ERROR_500)
                .

    }
}
