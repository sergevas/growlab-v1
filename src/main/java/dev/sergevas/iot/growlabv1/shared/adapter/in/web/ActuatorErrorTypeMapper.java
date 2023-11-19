package dev.sergevas.iot.growlabv1.shared.adapter.in.web;

import dev.sergevas.iot.growlabv1.shared.application.port.out.ActuatorException;
import dev.sergevas.iot.growlabv1.shared.application.service.ExceptionUtils;
import dev.sergevas.iot.growlabv1.shared.domain.ActuatorErrorType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Provider
public class ActuatorErrorTypeMapper implements ExceptionMapper<ActuatorException> {
    @Override
    public Response toResponse(ActuatorException e) {
        ActuatorErrorType sensorError = new ActuatorErrorType()
                .eventId(e.getEventId())
                .eventName(e.getMessage())
                .eventTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .desc(ExceptionUtils.getStackTrace(e.getCause()));
        return Response.serverError()
                .entity(sensorError)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
