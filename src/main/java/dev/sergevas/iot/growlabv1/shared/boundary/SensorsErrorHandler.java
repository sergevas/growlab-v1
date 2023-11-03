package dev.sergevas.iot.growlabv1.shared.boundary;

import dev.sergevas.iot.growlabv1.shared.application.port.out.SensorException;
import dev.sergevas.iot.growlabv1.shared.controller.SensorsErrorResponseBuilder;
import io.helidon.common.http.Http;
import io.helidon.webserver.ErrorHandler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

import javax.json.JsonObject;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class SensorsErrorHandler implements ErrorHandler<SensorException> {
    @Override
    public void accept(ServerRequest req, ServerResponse res, SensorException ex) {
        JsonObject returnObject = new SensorsErrorResponseBuilder()
                .sensorException(ex)
                .eventTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .buildJsonObject();
        res.status(Http.Status.INTERNAL_SERVER_ERROR_500).send(returnObject);
    }
}
