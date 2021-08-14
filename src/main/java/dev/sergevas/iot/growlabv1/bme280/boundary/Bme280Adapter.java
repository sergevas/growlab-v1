package dev.sergevas.iot.growlabv1.bme280.boundary;

import com.pi4j.util.StringUtil;
import dev.sergevas.iot.growlabv1.bme280.model.Bme280Readings;
import dev.sergevas.iot.growlabv1.hardware.boundary.I2CDeviceFactory;
import dev.sergevas.iot.growlabv1.performance.controller.Profiler;
import dev.sergevas.iot.growlabv1.shared.exception.SensorException;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;

import java.util.logging.Logger;
import java.util.stream.IntStream;

import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_BH1750_0001;

public class Bme280Adapter {
    private static final Logger LOG = Logger.getLogger(Bme280Adapter.class.getName());
    public static String INSTANCE_ID = "i2c-bus-GY-302-BH1750";
    public static final int GY_302_BH1750_ADDR = 0x23; // Default address for the GY-302 BH1750 chip
    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE_2 = (byte) 0x21;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE = (byte) 0x20;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    //TODO: implement
    public Bme280Readings getReadings() {
        Double lightIntensity;
        Bme280Readings bme280Readings = null;
        var i2cDevice = I2CDeviceFactory.create(INSTANCE_ID, GY_302_BH1750_ADDR);
        try {
            Profiler.init("getLightIntensity");
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
            throw new SensorException(E_BH1750_0001.getId(), SensorType.LIGHT, E_BH1750_0001.getName(), e);
        }
        return bme280Readings;
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
