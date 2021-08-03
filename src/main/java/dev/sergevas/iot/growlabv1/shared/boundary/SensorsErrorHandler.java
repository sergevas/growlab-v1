package dev.sergevas.iot.growlabv1.shared.boundary;

import dev.sergevas.iot.growlabv1.shared.controller.ErrorResponseBuilder;
import dev.sergevas.iot.growlabv1.shared.exception.SensorException;
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
        JsonObject returnObject = new ErrorResponseBuilder()
                .sensorException(ex)
                .eventTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .buildJsonObject();
        res.status(Http.Status.INTERNAL_SERVER_ERROR_500).send(returnObject);
    }
}
