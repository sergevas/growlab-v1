package dev.sergevas.iot.growlabv1.bme280.boundary;

import com.pi4j.io.i2c.I2C;
import com.pi4j.util.StringUtil;
import dev.sergevas.iot.growlabv1.bme280.model.*;
import dev.sergevas.iot.growlabv1.hardware.boundary.I2CDeviceFactory;
import dev.sergevas.iot.growlabv1.performance.controller.Profiler;
import dev.sergevas.iot.growlabv1.shared.controller.ExceptionUtils;
import dev.sergevas.iot.growlabv1.shared.exception.SensorException;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_BMEP280_0001;

public class Bmep280Adapter {
    private static final Logger LOG = Logger.getLogger(Bmep280Adapter.class.getName());
    public static String INSTANCE_ID = "i2c-bus-GY-BMEP280";
    public static final int ID_ADDR = 0xD0;
    public static final int CTRL_HUM_ADDR = 0xF2;
    public static final int PRES_MSB_ADDR = 0xF7;
    public static final int TEMP_MSB_ADDR = 0xFA;
    public static final int HUM_MSB_ADDR = 0xFD;
    public static final int CONFIG_ADDR = 0xF5;

    public static final int MODE_FORCED = 0x01;
    public static final int IIR_FILTER_OFF = 0x00;
    public static final int SPI_OFF = 0x00;


    //Forced mode:
    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE_2 = (byte) 0x21;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE = (byte) 0x20;
    public static final int GY_302_BH1750_READINGS_DATA_LENGTH = 2;

    private static  Bmep280Adapter instance;

    private int moduleAddress = 0x76; // Default address for the GY-BME/P280 module

    private CtrlMeasRegister ctrlMeasRegister;
    private CtrlHumRegister ctrlHumRegister;
    private ConfigRegister configRegister;
    private TrimmingParameters trimmingParameters;

    public static Bmep280Adapter getInstance() {
        if (instance == null) {
            instance = new Bmep280Adapter()
                    .ctrlMeasRegister(new CtrlMeasRegister()
                            .osrsT(Oversampling.OS_1.val())
                            .osrsP(Oversampling.OS_1.val())
                            .mode(Mode.FORCED_1.val()))
                    .ctrlHumRegister(new CtrlHumRegister()
                            .osrsH(Oversampling.OS_1.val()))
                    .configRegister(new ConfigRegister()
                            .spi3wEn(Spi3Wire.OFF.val())
                            .filter(Filter.OFF.val()))
                    .trimmingParameters(new TrimmingParameters())
                    .configure();
        }
        return instance;
    }

    public Bmep280Adapter moduleAddress(int moduleAddress) {
        this.moduleAddress = moduleAddress;
        return this;
    }

    public Bmep280Adapter ctrlMeasRegister(CtrlMeasRegister ctrlMeasRegister) {
        this.ctrlMeasRegister = ctrlMeasRegister;
        return this;
    }

    public Bmep280Adapter ctrlHumRegister(CtrlHumRegister ctrlHumRegister) {
        this.ctrlHumRegister = ctrlHumRegister;
        return this;
    }

    public Bmep280Adapter configRegister(ConfigRegister configRegister) {
        this.configRegister = configRegister;
        return this;
    }

    public Bmep280Adapter trimmingParameters(TrimmingParameters trimmingParameters) {
        this.trimmingParameters = trimmingParameters;
        return this;
    }

    private I2C getDeviceInstance() {
        return I2CDeviceFactory.getDeviceInstance(INSTANCE_ID, this.moduleAddress);
    }

    public void initSleepMode() {
        LOG.info("Init Sleep mode...");
        Profiler.init("Bmep280Adapter.initSleepMode");
        this.getDeviceInstance().writeRegister(CtrlMeasRegister.ADDR, Mode.SLEEP.val());
        LOG.info(Profiler.getCurrentMsg("Bmep280Adapter.initSleepMode", "initSleepModeComplete"));
    }

    public void initForcedMode() {
        LOG.info("Init Sleep mode...");
        Profiler.init("Bmep280Adapter.initForcedMode");
        this.getDeviceInstance().writeRegister(CtrlMeasRegister.ADDR, this.ctrlMeasRegister.getValue());
        LOG.info(Profiler.getCurrentMsg("Bmep280Adapter.initForcedMode", "initForcedModeComplete"));
    }

    public void readTrimmingParameters() {
        LOG.info("Reading Trimming Parameters...");
        Profiler.init("Bmep280Adapter.readTrimmingParameters");
        this.getDeviceInstance()
                .readRegister(TrimmingParameters.DIG_T1_ADDR,
                        this.trimmingParameters.getDigs(),
                        TrimmingParameters.DIG_T1_OFFSET,
                        TrimmingParameters.DIG_T1_CHUNK_LENGTH);
        this.getDeviceInstance()
                .readRegister(TrimmingParameters.DIG_H1_ADDR,
                        this.trimmingParameters.getDigs(),
                        TrimmingParameters.DIG_H1_OFFSET,
                        TrimmingParameters.DIG_H1_CHUNK_LENGTH);
        this.getDeviceInstance()
                .readRegister(TrimmingParameters.DIG_H2_ADDR,
                        this.trimmingParameters.getDigs(),
                        TrimmingParameters.DIG_H2_OFFSET,
                        TrimmingParameters.DIG_H2_CHUNK_LENGTH);
        this.trimmingParameters.init();
        LOG.info(Profiler.getCurrentMsg("Bmep280Adapter.readTrimmingParameters", "readTrimmingParametersComplete"));
    }

    public Bmep280Adapter configure() {
        LOG.info("Congigure the adapter...");
        Profiler.init("Bmep280Adapter.configure");
        this.initSleepMode();
        this.readTrimmingParameters();
        this.getDeviceInstance().writeRegister(CtrlHumRegister.ADDR, this.ctrlHumRegister.getValue());
        this.getDeviceInstance().writeRegister(ConfigRegister.ADDR, this.configRegister.getValue());
        this.getDeviceInstance().writeRegister(CtrlMeasRegister.ADDR, this.ctrlMeasRegister.getValue());
        LOG.info(Profiler.getCurrentMsg("Bmep280Adapter.configure", "configureComlete"));
        return this;
    }

    public String readModuleId() {
        String id = null;
        LOG.info("Reading module id...");
        Profiler.init("Bmep280Adapter.readModuleId");
        byte idRaw = this.getDeviceInstance().readRegisterByte(ID_ADDR);
        id = StringUtil.toHexString(idRaw);
        LOG.info(String.format("Module id=[%s]", id));
        LOG.info(Profiler.getCurrentMsg("Bmep280Adapter.readModuleId", "readModuleIdComplete"));
        return id;
    }

    public Bme280Readings getThpReadings() {
        Bme280Readings bme280Readings = new Bme280Readings();
        bme280Readings.id(this.readModuleId());
        try {
            Profiler.init("Bmep280Adapter.getThpReadings");
            var i2cDevice = this.getDeviceInstance();
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
            LOG.info(Profiler.getCurrentMsg("getLightIntensity", "fromRawReadingsToLightIntensity(readings)"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
            throw new SensorException(E_BMEP280_0001.getId(), SensorType.THP, E_BMEP280_0001.getName(), e);
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
