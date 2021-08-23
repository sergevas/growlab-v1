package dev.sergevas.iot.growlabv1.bh1750.boundary;

import com.pi4j.util.StringUtil;
import dev.sergevas.iot.growlabv1.hardware.boundary.I2CDeviceFactory;
import dev.sergevas.iot.growlabv1.performance.controller.Profiler;
import dev.sergevas.iot.growlabv1.shared.controller.ConfigHandler;
import dev.sergevas.iot.growlabv1.shared.controller.ExceptionUtils;
import dev.sergevas.iot.growlabv1.shared.exception.SensorException;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_BH1750_0001;

public class Bh1750Adapter {

    private static final Logger LOG = Logger.getLogger(Bh1750Adapter.class.getName());

    public static String INSTANCE_ID = "i2c-bus-GY-302-BH1750";
    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE = (byte) 0x20;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    private static Bh1750Adapter instance;

    private ConfigHandler configHandler;
    private Integer moduleAddress;

    private Bh1750Adapter() {
        super();
    }

    public static Bh1750Adapter create(Map<String, String> config) {
        if (instance == null) {
            instance = new Bh1750Adapter()
                    .configHandler(new ConfigHandler().configMap(config));
        }
        return instance;
    }

    public Bh1750Adapter configHandler(ConfigHandler configHandler) {
        this.configHandler = configHandler;
        return this;
    }

    public Integer getModuleAddress() {
        this.moduleAddress = Optional.ofNullable(this.moduleAddress)
                .orElse(this.configHandler.getAsInteger("moduleAddress"));
        return moduleAddress;
    }

    public Double getLightIntensity() {
        Double lightIntensity = null;
        var i2cDevice = I2CDeviceFactory.getDeviceInstance(INSTANCE_ID, this.getModuleAddress());
        try {
            Profiler.init("Bh1750Adapter.getLightIntensity()");
            i2cDevice.write(GY_302_BH1750_POWER_ON);
            i2cDevice.write(GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE);
            Thread.sleep(120); // H-Resolution Mode typical measurement time
            LOG.log(Level.FINE, "Reading data from GY-302 BH1750...");
            byte[] readings = i2cDevice.readNBytes(GY_302_BH1750_READINGS_DATA_LENGTH);
            LOG.log(Level.FINE, "GY-302 BH1750 readings: " + StringUtil.toHexString(readings));
            i2cDevice.write(GY_302_BH1750_POWER_DOWN);
            lightIntensity = fromRawReadingsToLightIntensity(readings);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
            throw new SensorException(E_BH1750_0001.getId(), SensorType.LIGHT, E_BH1750_0001.getName(), e);
        }
        LOG.log(Level.FINE, Profiler.getCurrentMsg("Bh1750Adapter.getLightIntensity()",
                "getLightIntensityComplete"));
        return lightIntensity;
    }

    public double fromRawReadingsToLightIntensity(byte[] i2cReadings) {
        Profiler.init("Bh1750Adapter.fromRawReadingsToLightIntensity()");
        double lightIntensity = Math.round((Byte.toUnsignedInt(i2cReadings[0]) << 8
                | Byte.toUnsignedInt(i2cReadings[1])) / 1.2 * 100.0) / 100.0;
        LOG.log(Level.FINE, "Light intensity: " + lightIntensity + " lux");
        LOG.log(Level.FINE, Profiler.getCurrentMsg("Bh1750Adapter.fromRawReadingsToLightIntensity()",
                "fromRawReadingsToLightIntensityComplete"));
        return lightIntensity;
    }
}
