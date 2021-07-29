package dev.sergevas.iot.bh1750.boundary;

import com.pi4j.Pi4J;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.util.Console;
import com.pi4j.util.StringUtil;

public class Bh1750Adapter {

    public static final int I2C_BUS = 1;
    public static final int GY_302_BH1750_ADDR = 0x23; // Default address for the GY-302 BH1750 chip
    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE_2 = (byte) 0x21;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    public String getLightIntensity() {
        String lightIntensity = null;
        final Console console = new Console();
        console.promptForExit();

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
            console.println("Reading data from GY-302 BH1750...");
            byte[] readings = i2cDevice.readNBytes(GY_302_BH1750_READINGS_DATA_LENGTH);
            console.println("GY-302 BH1750 readings: " + StringUtil.toHexString(readings));
            i2cDevice.write(GY_302_BH1750_POWER_DOWN);
            lightIntensity = StringUtil.toHexString(readings);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pi4j.shutdown();
        return lightIntensity;
    }
}
