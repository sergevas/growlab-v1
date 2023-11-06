package dev.sergevas.iot.growlabv1.shared.application.port.out;

public class HardwareException extends RuntimeException {

    public HardwareException(String message, Throwable cause) {
        super(message, cause);
    }

    public HardwareException(Throwable cause) {
        super(cause);
    }
}
