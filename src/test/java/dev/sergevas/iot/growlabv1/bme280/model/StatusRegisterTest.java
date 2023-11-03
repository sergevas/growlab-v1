package dev.sergevas.iot.growlabv1.bme280.model;

import dev.sergevas.iot.growlabv1.bme280.domain.StatusRegister;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatusRegisterTest {

    static StatusRegister statusRegisterRunning;
    static StatusRegister statusRegisterResultsTransfered;

    @BeforeAll
    static void setup() {
        statusRegisterRunning = new StatusRegister().val((byte) 0b01111011);
        statusRegisterResultsTransfered = new StatusRegister().val((byte) 0b01110011);
    }

    @Test
    void isConversationRunning() {
        assertTrue(statusRegisterRunning.isConversationRunning());
        assertFalse(statusRegisterRunning.isResultsTransferred());
    }

    @Test
    void isResultsTransfered() {
        assertTrue(statusRegisterResultsTransfered.isResultsTransferred());
        assertFalse(statusRegisterResultsTransfered.isConversationRunning());
    }
}
