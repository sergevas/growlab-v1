package dev.sergevas.iot.growlabv1.hardware.boundary;

public class HardwareInitException extends RuntimeException {

    public HardwareInitException() {
        super();
    }

    public HardwareInitException(String message) {
        super(message);
    }

    public HardwareInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public HardwareInitException(Throwable cause) {
        super(cause);
    }

    protected HardwareInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
