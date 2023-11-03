package dev.sergevas.iot.growlabv1.bh1750.adapter.in;

import dev.sergevas.iot.growlabv1.bh1750.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.growlabv1.shared.domain.SensorReadingsItemType;
import dev.sergevas.iot.growlabv1.shared.domain.SensorType;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/sensors/light")
public class Bh1750Resource {
    @Inject
    Bh1750UseCase bh1750UseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsItemType getBH1750AmbientLightIntensity() {
        var sensorReadingsItemTypeForBh1750 = bh1750UseCase.getSensorReadingsItemTypeForBh1750();
        SensorReadingsItemType sensorReadingsType = new SensorReadingsItemType()
                .sType(SensorType.LIGHT.name())
                .sTimestamp(sensorReadingsItemTypeForBh1750.lightIntensityTimestamp())
                .sData(String.valueOf(sensorReadingsItemTypeForBh1750.lightIntensity()));
        Log.info(sensorReadingsType);
        return sensorReadingsType;
    }
}
