package dev.sergevas.iot.growlabv1.hardware.boundary;

import dev.sergevas.iot.growlabv1.bh1750.boundary.Bh1750Adapter;
import dev.sergevas.iot.growlabv1.shared.controller.ExceptionUtils;
import dev.sergevas.iot.growlabv1.shared.exception.SensorException;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dev.sergevas.iot.growlabv1.shared.model.ErrorEventId.E_SYSTEM_0001;

public class SystemInfoAdapter {

    private static final Logger LOG = Logger.getLogger(Bh1750Adapter.class.getName());

    public static final String[] FETCH_CPU_TEMP_CMD = new String[] {"cat", "/sys/class/thermal/thermal_zone0/temp"};

    private static SystemInfoAdapter instance;

    private SystemInfoAdapter() {
        super();
    }

    public static SystemInfoAdapter getInstance() {
        if (instance == null) {
            instance = new SystemInfoAdapter();
        }
        return instance;
    }

    public Double getCpuTemp() {
        Double cpuTemp = null;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(FETCH_CPU_TEMP_CMD);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String cpuTempStr = reader.readLine();
            LOG.info("Have got CPU temperature readings: [" + cpuTempStr + "]");
            cpuTemp = Double.valueOf(cpuTempStr) / 1000.0;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
            throw new SensorException(E_SYSTEM_0001.getId(), SensorType.LIGHT, E_SYSTEM_0001.getName(), e);
        }
        LOG.info("Have got CPU temperature value: [" + cpuTemp + "]");
        return cpuTemp;
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            SystemInfoAdapter.getInstance().getCpuTemp();
            Thread.sleep(1000L);
        }
    }
}
