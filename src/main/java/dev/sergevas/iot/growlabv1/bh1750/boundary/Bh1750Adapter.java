package dev.sergevas.iot.growlabv1.bh1750.boundary;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.util.StringUtil;
import dev.sergevas.iot.growlabv1.shared.exception.SensorException;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;

import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Bh1750Adapter {
    private static final Logger LOG = Logger.getLogger(Bh1750Adapter.class.getName());
    public static final int I2C_BUS = 1;
    public static final int GY_302_BH1750_ADDR = 0x23; // Default address for the GY-302 BH1750 chip
    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE_2 = (byte) 0x21;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE = (byte) 0x20;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    public Double getLightIntensity() {
        Context pi4jContext = null;
        Double lightIntensity = null;
        try {
            Profiler.init("getLightIntensity");
            pi4jContext = Pi4J.newAutoContext();
            LOG.info(Profiler.getCurrentMsg("getLightIntensity", "Pi4J.newAutoContext()"));
            var config = I2C.newConfigBuilder(pi4jContext)
                    .id("growlab-i2c-bus")
                    .bus(I2C_BUS)
                    .device(GY_302_BH1750_ADDR)
                    .build();
            LOG.info(Profiler.getCurrentMsg("getLightIntensity", "I2C.newConfigBuilder()"));
            I2CProvider i2CProvider = pi4jContext.provider("pigpio-i2c");
            LOG.info(Profiler.getCurrentMsg("getLightIntensity", "Pi4J.provider()"));
            var i2cDevice = i2CProvider.create(config);
            LOG.info(Profiler.getCurrentMsg("getLightIntensity", "i2CProvider.create(config)"));
            i2cDevice.write(GY_302_BH1750_POWER_ON);
            Thread.sleep(1);
            i2cDevice.write(GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE);
            Thread.sleep(200);
            LOG.info("Reading data from GY-302 BH1750...");
            byte[] readings = i2cDevice.readNBytes(GY_302_BH1750_READINGS_DATA_LENGTH);
            LOG.info("GY-302 BH1750 readings...");
            IntStream.range(0, GY_302_BH1750_READINGS_DATA_LENGTH)
                .forEach(i -> LOG.info("readings[" + i + "]=" + StringUtil.toHexString(readings[i])));
            i2cDevice.write(GY_302_BH1750_POWER_DOWN);
            lightIntensity = fromRawReadingsToLightIntensity(readings);
            LOG.info(Profiler.getCurrentMsg("getLightIntensity", "fromRawReadingsToLightIntensity(readings)"));
        } catch (Exception e) {
            throw new SensorException("E-BH1750-0001", SensorType.LIGHT, "BH1750 data read error", e);
        }
        pi4jContext.shutdown();
        LOG.info(Profiler.getCurrentMsg("getLightIntensity", "pi4j.shutdown()"));
        return lightIntensity;
    }

    public double fromRawReadingsToLightIntensity(byte[] i2cReadings) {
        Profiler.init("fromRawReadingsToLightIntensity");
        double lightIntensity = Math.round((Byte.toUnsignedInt(i2cReadings[0]) << 8
                | Byte.toUnsignedInt(i2cReadings[1])) / 1.2 * 100.0) / 100.0;
        LOG.info("Light intensity: " + lightIntensity + " lux");
        LOG.info(Profiler.getCurrentMsg("fromRawReadingsToLightIntensity", "lightIntensity"));
        return lightIntensity;
    }
}
