package dev.sergevas.iot.growlabv1.camera.exception;

public class PiCamException extends RuntimeException {

    public PiCamException() {
    }

    public PiCamException(String message) {
        super(message);
    }

    public PiCamException(String message, Throwable cause) {
        super(message, cause);
    }

    public PiCamException(Throwable cause) {
        super(cause);
    }
}
