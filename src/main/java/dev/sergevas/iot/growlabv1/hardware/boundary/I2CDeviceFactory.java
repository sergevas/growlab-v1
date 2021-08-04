package dev.sergevas.iot.growlabv1.hardware.boundary;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CProvider;
import dev.sergevas.iot.growlabv1.bh1750.boundary.Bh1750Adapter;
import dev.sergevas.iot.growlabv1.performance.controller.Profiler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class I2CDeviceFactory {

    private static final Logger LOG = Logger.getLogger(Bh1750Adapter.class.getName());

    public static final int I2C_BUS = 1;

    private static Map<String, I2C> i2CDeviceInstances = new HashMap<>();

    private static Context pi4jContext;
    public static I2CProvider i2CProvider;

    public static I2C create(String instanceId, int i2CDeviceAddr) {
        return Optional.ofNullable(i2CDeviceInstances.get(instanceId))
                .orElseGet(() -> {
                    try {
                        Profiler.init("I2CDeviceFactory.create()");
                        Optional.ofNullable(pi4jContext).orElseGet(() -> {
                            pi4jContext = Pi4J.newAutoContext();
                            LOG.info(Profiler.getCurrentMsg("I2CDeviceFactory.create()", "Pi4J.newAutoContext()"));
                            i2CProvider = pi4jContext.provider("pigpio-i2c");
                            LOG.info(Profiler.getCurrentMsg("I2CDeviceFactory.create()", "Pi4J.provider()"));
                            return pi4jContext;
                        });
                        var config = I2C.newConfigBuilder(pi4jContext)
                                .id(instanceId)
                                .bus(I2C_BUS)
                                .device(i2CDeviceAddr)
                                .build();
                        LOG.info(Profiler.getCurrentMsg("I2CDeviceFactory.create()", "I2C.newConfigBuilder()"));
                        var i2cDevice = i2CProvider.create(config);
                        LOG.info(Profiler.getCurrentMsg("I2CDeviceFactory.create()", "i2CProvider.create(config)"));
                        i2CDeviceInstances.put(instanceId, i2cDevice);
                        return i2cDevice;
                    } catch (Exception e) {
                        throw new HardwareInitException("Unable to create I2C device", e);
                    }
                });
    }

    public static void close(String instanceId) {
        try {
            Optional.ofNullable(i2CDeviceInstances.get(instanceId)).ifPresent(I2C::close);
        } catch (Exception e) {
            throw new HardwareInitException("Unable to close I2C device");
        }
    }

    public static void shutdown() {
        try {
            Optional.ofNullable(pi4jContext).ifPresent(Context::shutdown);
        } catch (Exception e) {
            throw new HardwareInitException("Unable to shutdown Pi4J context");
        }
    }
}
