package dev.sergevas.iot.growlabv1.bme280.boundary;

import com.pi4j.io.i2c.I2C;
import com.pi4j.util.StringUtil;
import dev.sergevas.iot.growlabv1.bme280.model.Bme280Readings;
import dev.sergevas.iot.growlabv1.hardware.boundary.I2CDeviceFactory;
import dev.sergevas.iot.growlabv1.performance.controller.Profiler;
import dev.sergevas.iot.growlabv1.shared.exception.SensorException;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;

import java.util.logging.Logger;
import java.util.stream.IntStream;

import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_BMEP280_0001;
import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_BMEP280_0002;

public class Bmep280Adapter {
    private static final Logger LOG = Logger.getLogger(Bmep280Adapter.class.getName());
    public static String INSTANCE_ID = "i2c-bus-GY-BMEP280";
    public static final int GY_BMEP280_I2C_ADDR = 0x77; // An address for the GY-BME/P280 module
    public static final int ID_ADDR = 0xD0;
    public static final int CTRL_HUM_ADDR = 0xF2;
    public static final int CTRL_MEAS_ADDR = 0xF4;
    public static final int PRES_MSB_ADDR = 0xF7;
    public static final int TEMP_MSB_ADDR = 0xFA;
    public static final int HUM_MSB_ADDR = 0xFD;
    public static final int CONFIG_ADDR = 0xF5;

    public static final int MODE_SLEEP = 0x00;
    public static final int MODE_FORCED = 0x01;
    public static final int IIR_FILTER_OFF = 0x00;
    public static final int SPI_OFF = 0x00;


    //Forced mode:
    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE_2 = (byte) 0x21;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE = (byte) 0x20;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    private I2C getDeviceInstance() {
        return I2CDeviceFactory.getDeviceInstance(INSTANCE_ID, GY_BMEP280_I2C_ADDR);
    }

    public String readModuleId() {
        String id = null;
        try {
            LOG.info("Reading module id...");
            Profiler.init("Bmep280Adapter.readModuleId");
            byte idRaw = this.getDeviceInstance().readRegisterByte(ID_ADDR);
            LOG.info(Profiler.getCurrentMsg("Bmep280Adapter.readModuleId", "readByte()"));
            id = StringUtil.toHexString(idRaw);
            LOG.info(String.format("Module id=[%s]", id));
            return id;
        } catch (Exception e) {
            throw new SensorException(E_BMEP280_0001.getId(), SensorType.THP, E_BMEP280_0001.getName(), e);
        }
    }

    public Bme280Readings getThpReadings() {
        Bme280Readings bme280Readings = new Bme280Readings();
        bme280Readings.id(this.readModuleId());
//        try {
//            Profiler.init("Bmep280Adapter.getThpReadings");
//            var i2cDevice = this.getDeviceInstance();
//            i2cDevice.write(GY_302_BH1750_POWER_ON);
//            Thread.sleep(1);
//            i2cDevice.write(GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE);
//            Thread.sleep(200);
//            LOG.info("Reading data from GY-302 BH1750...");
//            byte[] readings = i2cDevice.readNBytes(GY_302_BH1750_READINGS_DATA_LENGTH);
//            LOG.info("GY-302 BH1750 readings...");
//            IntStream.range(0, GY_302_BH1750_READINGS_DATA_LENGTH)
//                .forEach(i -> LOG.info("readings[" + i + "]=" + StringUtil.toHexString(readings[i])));
//            i2cDevice.write(GY_302_BH1750_POWER_DOWN);
//            LOG.info(Profiler.getCurrentMsg("getLightIntensity", "fromRawReadingsToLightIntensity(readings)"));
//        } catch (Exception e) {
//            throw new SensorException(E_BMEP280_0002.getId(), SensorType.THP, E_BMEP280_0002.getName(), e);
//        }
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
