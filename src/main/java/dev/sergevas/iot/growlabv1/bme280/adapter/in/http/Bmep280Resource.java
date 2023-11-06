package dev.sergevas.iot.growlabv1.bme280.adapter.in.http;

import dev.sergevas.iot.growlabv1.bme280.application.port.in.Bmep280UseCase;
import dev.sergevas.iot.growlabv1.shared.domain.SensorReadingsItemType;
import dev.sergevas.iot.growlabv1.shared.domain.SensorReadingsType;
import dev.sergevas.iot.growlabv1.shared.domain.SensorType;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/sensors/thp")
public class Bmep280Resource {

    @Inject
    Bmep280UseCase bmep280UseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBmep280Thp() {
        var bme280Readings = bmep280UseCase.getThpReadings();
        var thpTimestamp = bme280Readings.getThpTimestamp();
        var id = bme280Readings.getId();
        var sensorReadingsType = new SensorReadingsType();
        sensorReadingsType.getSReadings().add(
                new SensorReadingsItemType()
                        .sId(id)
                        .sType(SensorType.TEMP.name())
                        .sData(bme280Readings.getTemperature().toString())
                        .sTimestamp(thpTimestamp));
        sensorReadingsType.getSReadings().add(
                new SensorReadingsItemType()
                        .sId(id)
                        .sType(SensorType.HUMID.name())
                        .sData(bme280Readings.getHumidity().toString())
                        .sTimestamp(thpTimestamp));
        sensorReadingsType.getSReadings().add(
                new SensorReadingsItemType()
                        .sId(id)
                        .sType(SensorType.PRESS.name())
                        .sData(bme280Readings.getPressure().toString())
                        .sTimestamp(thpTimestamp));
        Log.info(sensorReadingsType);
        return Response.ok(sensorReadingsType).build();
    }
}
