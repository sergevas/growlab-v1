package dev.sergevas.iot.growlabv1.bme280.boundary;

import dev.sergevas.iot.growlabv1.bme280.model.Bme280Readings;
import dev.sergevas.iot.growlabv1.shared.controller.SensorResponseBuilder;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

import javax.json.JsonObject;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class GetTHPRequestHandler implements Handler {

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        Bme280Readings readings = new Bmep280Adapter().getThpReadings();
        OffsetDateTime sTimestamp = OffsetDateTime.now(ZoneOffset.UTC);
        JsonObject returnObject = new SensorResponseBuilder()
                .item(new SensorResponseBuilder.Item()
                        .sId(readings.getId())
                        .sType(SensorType.TEMP)
                        .sData(readings.getTemperature())
                        .sTimestamp(sTimestamp))
                .item(new SensorResponseBuilder.Item()
                        .sId(readings.getId())
                        .sType(SensorType.HUMID)
                        .sData(readings.getHumidity())
                        .sTimestamp(sTimestamp))
                .item(new SensorResponseBuilder.Item()
                        .sId(readings.getId())
                        .sType(SensorType.PRESS)
                        .sData(readings.getPressure())
                        .sTimestamp(sTimestamp))
                .buildSensorReadings();
        res.send(returnObject);
    }
}