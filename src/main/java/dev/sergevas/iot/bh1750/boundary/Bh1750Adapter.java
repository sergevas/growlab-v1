package dev.sergevas.iot.bh1750.boundary;

import java.io.IOException;
import java.util.Arrays;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.util.Console;

//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Named;

public class Bh1750Adapter {

    public static final int GY_302_BH1750_ADDR = 0x23;
    public static final byte GY_302_BH1750_POWER_DOWN = 0x00;
    public static final byte GY_302_BH1750_POWER_ON = 0x01;
    public static final byte GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE_2 = (byte) 0x21;

    public static void main(String[] args) throws InterruptedException, IOException, UnsupportedBusNumberException {

        final Console console = new Console();
        console.promptForExit();
        try {
            int[] ids = I2CFactory.getBusIds();
            console.println("Found follow I2C busses: " + Arrays.toString(ids));
        } catch (IOException exception) {
            console.println("I/O error during fetch of I2C busses occurred");
        }

        for (int number = I2CBus.BUS_0; number <= I2CBus.BUS_17; ++number) {
            try {
                @SuppressWarnings("unused")
                I2CBus bus = I2CFactory.getInstance(number);
                console.println("Supported I2C bus " + number + " found");
            } catch (IOException exception) {
                console.println("I/O error on I2C bus " + number + " occurred");
            } catch (UnsupportedBusNumberException exception) {
                console.println("Unsupported I2C bus " + number + " required");
            }
        }

        I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);

        // create an I2C device for an individual device on the bus that you want to
        // communicate with
        // in this example we will use the default address for the GY-302 BH1750 chip
        // which is 0x23.
        I2CDevice device = i2c.getDevice(GY_302_BH1750_ADDR);
        device.write(GY_302_BH1750_POWER_ON);
        Thread.sleep(1);
        device.write(GY_302_BH1750_ONE_TIME_H_RESOLUTION_MODE_2);
        Thread.sleep(200);
        console.println("... reading DATA registers from GY-302 BH1750");
        byte[] readings = new byte[2];
        int bytesRead = device.read(readings, 0, 2);
        console.println("GY-302 BH1750 bytes read: " + bytesRead);
        console.println("GY-302 BH1750 readings: " + Arrays.toString(readings));
        // device.write(GY_302_BH1750_POWER_DOWN);
    }
}
