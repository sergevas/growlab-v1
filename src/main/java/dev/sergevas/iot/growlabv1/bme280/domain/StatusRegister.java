package dev.sergevas.iot.growlabv1.bme280.domain;

public class StatusRegister {

    public static final int ADDR = 0xF3;

    private byte val;

    public StatusRegister val(byte val) {
        this.val = val;
        return this;
    }

    public boolean isConversationRunning() {
        return (0x08 & val) == 0x08;
    }

    public boolean isResultsTransferred() {
        return (0x08 & val) == 0;
    }
}
