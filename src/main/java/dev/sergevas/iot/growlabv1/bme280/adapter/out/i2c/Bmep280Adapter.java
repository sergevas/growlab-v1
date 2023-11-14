package dev.sergevas.iot.growlabv1.bme280.adapter.out.i2c;

import dev.sergevas.iot.growlabv1.bme280.application.port.out.ThpReader;
import dev.sergevas.iot.growlabv1.bme280.domain.*;
import dev.sergevas.iot.growlabv1.performance.Profiler;
import dev.sergevas.iot.growlabv1.shared.application.port.out.SensorException;
import dev.sergevas.iot.growlabv1.shared.application.service.StringUtil;
import dev.sergevas.iot.growlabv1.shared.domain.SensorType;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.SMBus;
import io.quarkiverse.jef.java.embedded.framework.runtime.i2c.I2C;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

import static dev.sergevas.iot.growlabv1.bme280.domain.TrimmingParameters.*;
import static dev.sergevas.iot.growlabv1.shared.domain.ErrorEventId.E_BMEP280_0001;
import static dev.sergevas.iot.growlabv1.shared.domain.ErrorEventId.E_BMEP280_0002;
import static io.quarkiverse.jef.java.embedded.framework.linux.core.SmBusConstants.I2C_SMBUS_READ;

@ApplicationScoped
@IfBuildProfile("prod")
public class Bmep280Adapter implements ThpReader {
    public static final int ID_ADDR = 0xD0;
    public static final int H4_H5_DATA_LENGTH = 3;

    private CtrlMeasRegister ctrlMeasRegister;
    private CtrlHumRegister ctrlHumRegister;
    private ConfigRegister configRegister;
    private TrimmingParameters trimmingParameters;
    private Bmep280RawReadings bme280RawReadings;
    private StatusRegister statusRegister;

    @I2C(name = "i2c1")
    I2CBus i2C0Bus;
    @ConfigProperty(name = "bme280.moduleAddress")
    int bme280moduleAddress;
    @ConfigProperty(name = "bme280.forcedModeTimeout")
    long forcedModeTimeout;
    private boolean isConfigurationApplied;

    @PostConstruct
    public void init() {
        ctrlMeasRegister = new CtrlMeasRegister()
                .osrsT(Oversampling.OS_1.val())
                .osrsP(Oversampling.OS_1.val())
                .mode(Mode.FORCED_1.val());
        ctrlHumRegister = new CtrlHumRegister()
                .osrsH(Oversampling.OS_1.val());
        configRegister = new ConfigRegister()
                .spi3wEn(Spi3Wire.OFF.val())
                .filter(Filter.OFF.val());
        trimmingParameters = new TrimmingParameters();
        statusRegister = new StatusRegister();
        bme280RawReadings = new Bmep280RawReadings();
    }

    public void initSleepMode(SMBus smBus) throws IOException {
        Log.debug("Init Sleep mode...");
        Profiler.init("Bmep280Adapter.initSleepMode");
        smBus.writeByteData(CtrlMeasRegister.ADDR, Mode.SLEEP.val());
        Log.debug(Profiler.getCurrentMsg("Bmep280Adapter.initSleepMode", "initSleepModeComplete"));
    }

    public void initForcedMode(SMBus smBus) throws IOException {
        Log.debug("Init Sleep mode...");
        Profiler.init("Bmep280Adapter.initForcedMode");
        smBus.writeByteData(CtrlMeasRegister.ADDR, this.ctrlMeasRegister.getValue());
        Log.debug(Profiler.getCurrentMsg("Bmep280Adapter.initForcedMode", "initForcedModeComplete"));
    }

    public void readTrimmingParameters(SMBus smBus) throws IOException {
        Log.debug("Reading Trimming Parameters...");
        Profiler.init("Bmep280Adapter.readTrimmingParameters");
        trimmingParameters.digT1(toUnsigned(smBus.readWordData(DIG_T1_ADDR)));
        trimmingParameters.digT2(smBus.readWordData(DIG_T2_ADDR));
        trimmingParameters.digT3(smBus.readWordData(DIG_T3_ADDR));
        trimmingParameters.digP1(toUnsigned(smBus.readWordData(DIG_P1_ADDR)));
        trimmingParameters.digP2(smBus.readWordData(DIG_P2_ADDR));
        trimmingParameters.digP3(smBus.readWordData(DIG_P3_ADDR));
        trimmingParameters.digP4(smBus.readWordData(DIG_P4_ADDR));
        trimmingParameters.digP5(smBus.readWordData(DIG_P5_ADDR));
        trimmingParameters.digP6(smBus.readWordData(DIG_P6_ADDR));
        trimmingParameters.digP7(smBus.readWordData(DIG_P7_ADDR));
        trimmingParameters.digP8(smBus.readWordData(DIG_P8_ADDR));
        trimmingParameters.digP9(smBus.readWordData(DIG_P9_ADDR));
        trimmingParameters.digH1(toUnsigned(smBus.readByteData(DIG_H1_ADDR)));
        trimmingParameters.digH2(smBus.readWordData(DIG_H2_ADDR));
        trimmingParameters.digH3(toUnsigned(smBus.readByteData(DIG_H3_ADDR)));
        trimmingParameters.digH6(smBus.readByteData(DIG_H6_ADDR));

        byte[] h4h5Data = new byte[H4_H5_DATA_LENGTH];
        smBus.i2cSmbusAccess(I2C_SMBUS_READ, DIG_H4_ADDR, H4_H5_DATA_LENGTH, h4h5Data);
        trimmingParameters.digH4(toDigH4(h4h5Data[1], h4h5Data[0]));
        trimmingParameters.digH5(toDigH5(h4h5Data[1], h4h5Data[2]));

        Log.debugf("trimmingParameters: %s", trimmingParameters);
        Log.debug(Profiler.getCurrentMsg("Bmep280Adapter.readTrimmingParameters",
                "readTrimmingParametersComplete"));
    }

    public void configure(SMBus smBus) throws IOException {
        Log.debug("Configure the adapter...");
        this.initSleepMode(smBus);
        this.readTrimmingParameters(smBus);
        Profiler.init("Bmep280Adapter.configure");
        smBus.writeByteData(CtrlHumRegister.ADDR, this.ctrlHumRegister.getValue());
        smBus.writeByteData(ConfigRegister.ADDR, this.configRegister.getValue());
        smBus.writeByteData(CtrlMeasRegister.ADDR, this.ctrlMeasRegister.getValue());
        isConfigurationApplied = true;
        Log.debug(Profiler.getCurrentMsg("Bmep280Adapter.configure", "configureComplete"));
    }

    public String readModuleId(SMBus smBus) throws IOException {
        Log.debug("Reading module id...");
        Profiler.init("Bmep280Adapter.readModuleId");
        int idRaw = smBus.readByteData(ID_ADDR);
        String id = StringUtil.toHexString(idRaw);
        Log.debugf("Module id=[%s]", id);
        Log.debug(Profiler.getCurrentMsg("Bmep280Adapter.readModuleId", "readModuleIdComplete"));
        return id;
    }

    public boolean isMeasurementInProgress(SMBus smBus) throws IOException {
        return this.statusRegister.val(smBus.readByteData(StatusRegister.ADDR)).isConversationRunning();
    }

    public void readRawData(SMBus smBus) throws IOException, InterruptedException {
        this.initForcedMode(smBus);
        Log.debug("Burst read raw data...");
        Profiler.init("Bmep280Adapter.readRawData");
        long timeoutStartTime = System.currentTimeMillis();
        while (this.isMeasurementInProgress(smBus)) {
            if ((System.currentTimeMillis() - timeoutStartTime) > forcedModeTimeout) {
                throw new SensorException(E_BMEP280_0002.getId(), SensorType.THP, E_BMEP280_0001.getName());
            }
            Thread.sleep(1L);
        }
        smBus.i2cSmbusAccess(I2C_SMBUS_READ, Bmep280RawReadings.ADDR, Bmep280RawReadings.READINGS_LENGTH,
                this.bme280RawReadings.getReadings());
        Log.debugf("Raw readings=[%s]", this.bme280RawReadings.getReadings());
        Log.debug(Profiler.getCurrentMsg("Bmep280Adapter.readRawData", "readRawDataComplete"));

    }

    public Bmep280Readings readThp() {
        Bmep280Readings bme280Readings;
        Profiler.init("Bmep280Adapter.getThpReadings");
        try {
            SMBus smBus = i2C0Bus.select(bme280moduleAddress).getSmBus();
            if (!isConfigurationApplied) {
                this.configure(smBus);
            }
            this.readRawData(smBus);
            this.bme280RawReadings.computeAdcValues();
            ReadingsProcessor readingsProcessor = new ReadingsProcessor()
                    .bme280RawReadings(this.bme280RawReadings)
                    .trimmingParameters(this.trimmingParameters);
            bme280Readings = readingsProcessor.compensateReadings();
            bme280Readings.id(this.readModuleId(smBus));
            Log.debug(Profiler.getCurrentMsg("Bmep280Adapter.getThpReadings", "getThpReadingsComplete"));
            return bme280Readings;
        } catch (Exception e) {
            Log.error(e);
            throw new SensorException(E_BMEP280_0001.getId(), SensorType.THP, E_BMEP280_0001.getName(), e);
        }
    }
}
