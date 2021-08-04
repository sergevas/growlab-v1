package dev.sergevas.iot.growlabv1.hardware.boundary;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CProvider;
import dev.sergevas.iot.growlabv1.bh1750.boundary.Bh1750Adapter;
import dev.sergevas.iot.growlabv1.performance.controller.Profiler;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dev.sergevas.iot.growlabv1.bh1750.boundary.Bh1750Adapter.I2C_BUS;

public class I2CDeviceFactory {

    private static final Logger LOG = Logger.getLogger(Bh1750Adapter.class.getName());

    public static final int GY_302_BH1750_ADDR = 0x23; // Default address for the GY-302 BH1750 chip

    private static Context pi4jContext = null;
    private static I2C i2cDevice = null;

    public static I2C create() {
        i2cDevice.close();
        return Optional.of(i2cDevice).orElseGet(() -> {
            try {
                Profiler.init("getLightIntensity");
                pi4jContext = Pi4J.newAutoContext();
                LOG.info(Profiler.getCurrentMsg("getLightIntensity", "Pi4J.newAutoContext()"));
                var config = I2C.newConfigBuilder(pi4jContext)
                        .id("growlab-i2c-bus")
                        .bus(I2C_BUS)
                        .device(GY_302_BH1750_ADDR)
                        .build();
                LOG.info(Profiler.getCurrentMsg("getLightIntensity", "I2C.newConfigBuilder()"));
                I2CProvider i2CProvider = pi4jContext.provider("pigpio-i2c");
                LOG.info(Profiler.getCurrentMsg("getLightIntensity", "Pi4J.provider()"));
                var i2cDevice = i2CProvider.create(config);
                LOG.info(Profiler.getCurrentMsg("getLightIntensity", "i2CProvider.create(config)"));
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Unable to create I2C device");
            }
        })
    }

    public static
}
        try {
        Profiler.init("getLightIntensity");
        pi4jContext = Pi4J.newAutoContext();
        LOG.info(Profiler.getCurrentMsg("getLightIntensity", "Pi4J.newAutoContext()"));
        var config = I2C.newConfigBuilder(pi4jContext)
                .id("growlab-i2c-bus")
                .bus(I2C_BUS)
                .device(GY_302_BH1750_ADDR)
                .build();
        LOG.info(Profiler.getCurrentMsg("getLightIntensity", "I2C.newConfigBuilder()"));
        I2CProvider i2CProvider = pi4jContext.provider("pigpio-i2c");
        LOG.info(Profiler.getCurrentMsg("getLightIntensity", "Pi4J.provider()"));
        var i2cDevice = i2CProvider.create(config);
        LOG.info(Profiler.getCurrentMsg("getLightIntensity", "i2CProvider.create(config)"));
}
