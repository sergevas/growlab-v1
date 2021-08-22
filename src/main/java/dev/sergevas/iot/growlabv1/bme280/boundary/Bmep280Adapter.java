package dev.sergevas.iot.growlabv1.bme280.boundary;

import com.pi4j.io.i2c.I2C;
import com.pi4j.util.StringUtil;
import dev.sergevas.iot.growlabv1.bme280.controller.ReadingsProcessor;
import dev.sergevas.iot.growlabv1.bme280.model.*;
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
import java.util.stream.IntStream;

import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_BMEP280_0001;
import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_BMEP280_0002;

public class Bmep280Adapter {

    private static final Logger LOG = Logger.getLogger(Bmep280Adapter.class.getName());

    public static String INSTANCE_ID = "i2c-bus-GY-BMEP280";
    public static final int ID_ADDR = 0xD0;

    private static  Bmep280Adapter instance;

    private ConfigHandler configHandler;

    private Integer moduleAddress;
    private Long forcedModeTimeout;

    private CtrlMeasRegister ctrlMeasRegister;
    private CtrlHumRegister ctrlHumRegister;
    private ConfigRegister configRegister;
    private TrimmingParameters trimmingParameters;
    private Bme280RawReadings bme280RawReadings;
    private StatusRegister statusRegister;

    private Bmep280Adapter(){
        super();
    }

    public static Bmep280Adapter create(Map<String, String> config) {
        if (instance == null) {
            instance = new Bmep280Adapter()
                    .configHandler(new ConfigHandler().configMap(config))
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
                    .statusRegister(new StatusRegister())
                    .bme280RawReadings(new Bme280RawReadings())
                    .configure();
        }
        return instance;
    }

    public Bmep280Adapter configHandler(ConfigHandler configHandler) {
        this.configHandler = configHandler;
        return this;
    }

    public Integer getModuleAddress() {
        this.moduleAddress = Optional.ofNullable(this.moduleAddress)
                .orElse(this.configHandler.getAsInteger("moduleAddress"));
        return moduleAddress;
    }

    public Long getForcedModeTimeout() {
        this.forcedModeTimeout = Optional.ofNullable(this.forcedModeTimeout)
                .orElse(this.configHandler.getAsLong("forcedModeTimeout"));
        return forcedModeTimeout;
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

    public Bmep280Adapter bme280RawReadings(Bme280RawReadings bme280RawReadings) {
        this.bme280RawReadings = bme280RawReadings;
        return this;
    }

    public Bmep280Adapter statusRegister(StatusRegister statusRegister) {
        this.statusRegister = statusRegister;
        return this;
    }

    private I2C getDeviceInstance() {
        return I2CDeviceFactory.getDeviceInstance(INSTANCE_ID, this.getModuleAddress());
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
        byte[] digs = this.trimmingParameters.getDigs();
        IntStream.range(0, digs.length)
                .forEach(i -> LOG.info("digs[" + i + "]=" + StringUtil.toHexString(digs[i])));
        LOG.info(Profiler.getCurrentMsg("Bmep280Adapter.readTrimmingParameters", "readTrimmingParametersComplete"));
    }

    public Bmep280Adapter configure() {
        LOG.info("Congigure the adapter...");
        this.initSleepMode();
        this.readTrimmingParameters();
        Profiler.init("Bmep280Adapter.configure");
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

    public boolean isMeasurementInProgress() {
        return this.statusRegister
                .val(this.getDeviceInstance().readRegisterByte(StatusRegister.ADDR))
                .isConversationRunning();
    }

    public void readRawData() {
        this.initForcedMode();
        LOG.info("Burst read raw data...");
        Profiler.init("Bmep280Adapter.readRawData");
        Long timeoutStartTime = System.currentTimeMillis();
        try {
            while (this.isMeasurementInProgress()) {
                if ((System.currentTimeMillis() - timeoutStartTime) > this.getForcedModeTimeout()) {
                    throw new SensorException(E_BMEP280_0002.getId(), SensorType.THP, E_BMEP280_0001.getName());
                }
                Thread.sleep(1L);
            }
            this.getDeviceInstance().readRegister(Bme280RawReadings.ADDR, this.bme280RawReadings.getReadings());
            LOG.info(String.format("Raw readings=[%s]", StringUtil.toHexString(this.bme280RawReadings.getReadings())));
            LOG.info(Profiler.getCurrentMsg("Bmep280Adapter.readRawData", "readRawDataComplete"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
            if (e instanceof SensorException) {
                throw (SensorException)e;
            } else {
                throw new SensorException(E_BMEP280_0001.getId(), SensorType.THP, E_BMEP280_0001.getName(), e);
            }
        }
    }

    public Bme280Readings getThpReadings() {
        Bme280Readings bme280Readings = null;
        this.readRawData();
        Profiler.init("Bmep280Adapter.getThpReadings");
        try {
            this.bme280RawReadings.computeAdcValues();
            ReadingsProcessor readingsProcessor = new ReadingsProcessor()
                    .bme280RawReadings(this.bme280RawReadings)
                    .trimmingParameters(this.trimmingParameters);
            bme280Readings = readingsProcessor.compensateReadings();
            bme280Readings.id(this.readModuleId());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
            throw new SensorException(E_BMEP280_0001.getId(), SensorType.THP, E_BMEP280_0001.getName(), e);
        }
        LOG.info(Profiler.getCurrentMsg("Bmep280Adapter.getThpReadings", "getThpReadingsComplete"));
        return bme280Readings;
    }
}
