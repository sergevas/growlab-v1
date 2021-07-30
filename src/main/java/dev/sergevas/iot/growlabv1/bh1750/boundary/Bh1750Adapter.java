package dev.sergevas.iot.growlabv1.bh1750.boundary;

import com.pi4j.Pi4J;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.util.StringUtil;
import dev.sergevas.iot.growlabv1.shared.exception.SensorException;

import java.util.logging.Logger;

public class Bh1750Adapter {
    private static final Logger LOG = Logger.getLogger(Bh1750Adapter.class.getName());
    public static final int I2C_BUS = 1;
    public static final int GY_302_BH1750_ADDR = 0x23; // Default address for the GY-302 BH1750 chip
    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE_2 = (byte) 0x21;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    public String getLightIntensity() {
        String lightIntensity = null;

        var pi4j = Pi4J.newAutoContext();
        var config = I2C.newConfigBuilder(pi4j)
                .id("growlab-i2c-bus")
                .bus(I2C_BUS)
                .device(GY_302_BH1750_ADDR)
                .build();
        I2CProvider i2CProvider = pi4j.provider("pigpio-i2c");
        try (var i2cDevice = i2CProvider.create(config)) {
            i2cDevice.write(GY_302_BH1750_POWER_ON);
            Thread.sleep(1);
            i2cDevice.write(GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE_2);
            Thread.sleep(200);
            LOG.info("Reading data from GY-302 BH1750...");
            byte[] readings = i2cDevice.readNBytes(GY_302_BH1750_READINGS_DATA_LENGTH);
            LOG.info("GY-302 BH1750 readings: " + StringUtil.toHexString(readings));
            i2cDevice.write(GY_302_BH1750_POWER_DOWN);
            lightIntensity = StringUtil.toHexString(readings);
        } catch (InterruptedException e) {
            throw new SensorException(e);
        }
        pi4j.shutdown();
        return lightIntensity;
    }
}
