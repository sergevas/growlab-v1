package dev.sergevas.iot.growlabv1.sender.boundary;

import dev.sergevas.iot.growlabv1.hardware.boundary.HardwareException;
import dev.sergevas.iot.growlabv1.sender.entity.SensorReadings;
import dev.sergevas.iot.growlabv1.shared.controller.ConfigHandler;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.logging.Logger;

public class SensorDataFileDumpAdapter {

    private static final Logger LOG = Logger.getLogger(SensorDataFileDumpAdapter.class.getName());

    private static SensorDataFileDumpAdapter instance;

    private ConfigHandler configHandler;

    public static SensorDataFileDumpAdapter create(Map<String, String> config) {
        if (instance == null) {
            instance = new SensorDataFileDumpAdapter()
                    .configHandler(new ConfigHandler().configMap(config));
        }
        return instance;
    }

    public SensorDataFileDumpAdapter configHandler(ConfigHandler configHandler) {
        this.configHandler = configHandler;
        return this;
    }

    public void writeDataToFile(SensorReadings readings, byte[] image) {
        LOG.info("Enter writeDataToFile with readings: " + readings);
        var filePath = configHandler.getAsString("basePath");
        try (var fos = Files.newOutputStream(Path.of(filePath, "data",
                LocalDate.now().toString(), LocalTime.now() + ".json"))) {
            JsonGenerator jsonGenerator = Json.createGenerator(fos);
            jsonGenerator.writeStartObject();
            jsonGenerator.write("temperature", readings.getTemperature());
            jsonGenerator.write("humidity", readings.getHumidity());
            jsonGenerator.write("pressure", readings.getPressure());
            jsonGenerator.write("light", readings.getLight());
            jsonGenerator.write("timestamp", readings.getTimestamp().toString());
            jsonGenerator.write("cameraMode", readings.getCameraMode().toString());
            jsonGenerator.writeEnd();
            LOG.info("Successfully wrote sensor data to file: " + filePath);
        } catch (IOException e) {
            throw new HardwareException("Failed to write sensor data to file", e);
        }
        if (image != null) {
            try {
                Files.write(Path.of(filePath, "images",
                        LocalDate.now().toString(), LocalTime.now() + ".jpg"), image);
            } catch (IOException e) {
                throw new HardwareException("Failed to write image data to file", e);
            }
        }
    }
}
