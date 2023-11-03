package dev.sergevas.iot.growlabv1.shared.adapter.in.web;

import dev.sergevas.iot.growlabv1.shared.application.port.out.SensorException;
import dev.sergevas.iot.growlabv1.shared.application.service.ExceptionUtils;
import dev.sergevas.iot.growlabv1.shared.domain.SensorErrorType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Provider
public class SensorErrorTypeMapper implements ExceptionMapper<SensorException> {
    @Override
    public Response toResponse(SensorException e) {
        SensorErrorType sensorError = new SensorErrorType()
                .eventId(e.getEventId())
                .eventName(e.getMessage())
                .eventTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .sType(e.getSensorType().name())
                .desc(ExceptionUtils.getStackTrace(e.getCause()));
        return Response.serverError().entity(sensorError).build();
    }
}
