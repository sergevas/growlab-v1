package dev.sergevas.iot.growlabv1.bh1750.adapter.out.i2c;

import dev.sergevas.iot.growlabv1.bh1750.application.port.out.LightIntensity;
import dev.sergevas.iot.growlabv1.performance.Profiler;
import dev.sergevas.iot.growlabv1.shared.application.port.out.SensorException;
import dev.sergevas.iot.growlabv1.shared.application.service.StringUtil;
import dev.sergevas.iot.growlabv1.shared.domain.ErrorEventId;
import dev.sergevas.iot.growlabv1.shared.domain.SensorType;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CInterface;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.SMBus;
import io.quarkiverse.jef.java.embedded.framework.runtime.i2c.I2C;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.ByteBuffer;

@ApplicationScoped
@IfBuildProfile("prod")
public class Bh1750Adapter implements LightIntensity {
    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE = (byte) 0x20;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    @I2C(name = "i2c0")
    I2CBus i2C0Bus;
    @ConfigProperty(name = "bh1750.moduleAddress")
    int bh1750ModuleAddress;

    @Override
    public double getLightIntensity() {
        try {
            final SMBus smBus = i2C0Bus.select(bh1750ModuleAddress).getSmBus();
            Profiler.init("Bh1750Adapter.getLightIntensity()");
            smBus.writeByte(GY_302_BH1750_POWER_ON);
            smBus.writeByte(GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE);
            Thread.sleep(180); // H-Resolution Mode max measurement time
            Log.debug("Reading data from GY-302 BH1750...");
            byte[] readings = new byte[GY_302_BH1750_READINGS_DATA_LENGTH];
            I2CInterface i2CInterface = smBus.getInterface();
            i2CInterface.read(ByteBuffer.wrap(readings), GY_302_BH1750_READINGS_DATA_LENGTH);
            Log.infof("GY-302 BH1750 readings: %s", StringUtil.toHexString(readings));
            smBus.writeByte(GY_302_BH1750_POWER_DOWN);
            double lightIntensity = fromRawReadingsToLightIntensity(readings);
            Log.debug(Profiler.getCurrentMsg("Bh1750Adapter.getLightIntensity()", "getLightIntensityComplete"));
            return lightIntensity;
        } catch (Exception e) {
            Log.error("Unable to get light intensity", e);
            throw new SensorException(ErrorEventId.E_BH1750_0001.getId(), SensorType.LIGHT, ErrorEventId.E_BH1750_0001.getName(), e);
        }
    }

    public double fromRawReadingsToLightIntensity(byte[] i2cReadings) {
        Profiler.init("Bh1750Adapter.fromRawReadingsToLightIntensity()");
        double lightIntensity = Math.round((Byte.toUnsignedInt(i2cReadings[0]) << 8
                | Byte.toUnsignedInt(i2cReadings[1])) / 1.2 * 100.0) / 100.0;
        Log.debugf("Light intensity: %d lux", lightIntensity);
        Log.debug(Profiler.getCurrentMsg("Bh1750Adapter.fromRawReadingsToLightIntensity()",
                "fromRawReadingsToLightIntensityComplete"));
        return lightIntensity;
    }
}
