package dev.sergevas.iot.growlabv1.hardware.boundary;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;

import java.util.Optional;

public class Pi4JContextFactory {

    private static Context pi4jContext;

    public static Context create() {
        return Optional.ofNullable(pi4jContext)
                .orElseGet(() -> {
                    try {
                        pi4jContext = Pi4J.newAutoContext();
                    } catch (Exception e) {
                        throw new HardwareException("Unable to create a new Pi4J context");
                    }
                return pi4jContext;
        });
    }

    public static void shutdown() {
        try {
            Optional.ofNullable(pi4jContext).ifPresent(Context::shutdown);
        } catch (Exception e) {
            throw new HardwareException("Unable to shutdown Pi4J context");
        }
    }


}
