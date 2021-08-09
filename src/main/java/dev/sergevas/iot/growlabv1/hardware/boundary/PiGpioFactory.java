package dev.sergevas.iot.growlabv1.hardware.boundary;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import dev.sergevas.iot.growlabv1.performance.controller.Profiler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class PiGpioFactory {

    private static final Logger LOG = Logger.getLogger(PiGpioFactory.class.getName());

    private static Map<String, DigitalOutput> digitalOutputInstances = new HashMap<>();

    private static Context pi4jContext;

    public static DigitalOutput createOutputInstance(String instanceId, int pinAddress) {
        return Optional.ofNullable(digitalOutputInstances.get(instanceId))
                .orElseGet(() -> {
                    try {
                        Profiler.init("GpioFactory.createOutputInstance()");
                        Optional.ofNullable(pi4jContext).orElseGet(() -> {
                            pi4jContext = Pi4JContextFactory.create();
                            LOG.info(Profiler.getCurrentMsg("GpioFactory.createOutputInstance()", "Pi4J.newAutoContext()"));
                            return pi4jContext;
                        });
                        var ledConfig = DigitalOutput.newConfigBuilder(pi4jContext)
                                .id(instanceId)
                                .address(pinAddress)
                                .shutdown(DigitalState.HIGH)
                                .initial(DigitalState.HIGH)
                                .provider("pigpio-digital-output");
                        var digitalOutput = pi4jContext.create(ledConfig);
                        LOG.info(Profiler.getCurrentMsg("GpioFactory.createOutputInstance()", "DigitalOutput.newConfigBuilder()"));
                        digitalOutputInstances.put(instanceId, digitalOutput);
                        return digitalOutput;
                    } catch (Exception e) {
                        throw new HardwareException("Unable to create DigitalOutput instance", e);
                    }
                });
    }
}
