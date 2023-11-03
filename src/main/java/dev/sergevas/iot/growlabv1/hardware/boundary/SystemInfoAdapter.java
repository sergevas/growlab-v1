package dev.sergevas.iot.growlabv1.hardware.boundary;

import dev.sergevas.iot.growlabv1.performance.controller.Profiler;
import dev.sergevas.iot.growlabv1.shared.application.port.out.SensorException;
import dev.sergevas.iot.growlabv1.shared.controller.ExceptionUtils;
import dev.sergevas.iot.growlabv1.shared.domain.SensorType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dev.sergevas.iot.growlabv1.shared.domain.ErrorEventId.E_SYSTEM_0001;

public class SystemInfoAdapter {

    private static final Logger LOG = Logger.getLogger(SystemInfoAdapter.class.getName());

    public static final String[] FETCH_CPU_TEMP_CMD = new String[]{"cat", "/sys/class/thermal/thermal_zone0/temp"};

    private static SystemInfoAdapter instance;

    public ProcessBuilder processBuilder;

    private SystemInfoAdapter() {
        super();
    }

    public static SystemInfoAdapter create() {
        if (instance == null) {
            instance = new SystemInfoAdapter();
        }
        return instance;
    }

    public ProcessBuilder getProcessBuilder() {
        this.processBuilder = Optional.ofNullable(this.processBuilder)
                .orElseGet(() -> new ProcessBuilder().command(FETCH_CPU_TEMP_CMD));
        return processBuilder;
    }

    public String getCpuTemp() {
        Profiler.init("SystemInfoAdapter.getCpuTemp()");
        String cpuTemp = null;
        try {
            Process process = this.getProcessBuilder().start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            LOG.log(Level.FINE, Profiler.getCurrentMsg("SystemInfoAdapter.getCpuTemp()", "createBufferedReader"));
            String cpuTempStr = reader.readLine();
            cpuTemp = String.valueOf(Double.parseDouble(cpuTempStr) / 1000.0);
            LOG.log(Level.FINE, Profiler.getCurrentMsg("SystemInfoAdapter.getCpuTemp()", "calcCpuTemp"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
            throw new SensorException(E_SYSTEM_0001.getId(), SensorType.LIGHT, E_SYSTEM_0001.getName(), e);
        }
        LOG.log(Level.FINE, Profiler.getCurrentMsg("SystemInfoAdapter.getCpuTemp()", "getCpuTempComplete"));
        return cpuTemp;
    }
}
