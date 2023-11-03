package dev.sergevas.iot.growlabv1.health.adapter.in.http;

import dev.sergevas.iot.growlabv1.health.application.port.in.SystemInfoUseCase;
import dev.sergevas.iot.growlabv1.health.domain.SystemInfo;
import dev.sergevas.iot.growlabv1.shared.domain.SensorReadingsItemType;
import dev.sergevas.iot.growlabv1.shared.domain.SensorReadingsType;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Path("/health")
public class SystemInfoResource {

    @Inject
    SystemInfoUseCase systemInfoUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsType getSystemData() {
        SystemInfo systemInfo = systemInfoUseCase.getSystemInfo();
        SensorReadingsType sensorReadingsType = new SensorReadingsType()
                .addSReadingsItem(new SensorReadingsItemType()
                        .sType(SensorType.CPU_TEMP.name())
                        .sName(SensorName.ORANGE_PI_ZERO.getName())
                        .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                        .sData(String.valueOf(systemInfo.getCpuTemp())));
        Log.info(sensorReadingsType);
        return sensorReadingsType;
    }
}
