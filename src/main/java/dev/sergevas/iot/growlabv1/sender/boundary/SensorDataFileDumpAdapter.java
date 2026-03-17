package dev.sergevas.iot.growlabv1.sender.boundary;

import dev.sergevas.iot.growlabv1.hardware.boundary.HardwareException;
import dev.sergevas.iot.growlabv1.sender.entity.SensorReadings;
import dev.sergevas.iot.growlabv1.shared.controller.ConfigHandler;

import javax.json.Json;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        var basePath = configHandler.getAsString("basePath");
        var currentDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        var currentTime = DateTimeFormatter.ofPattern("HHmmss").format(LocalTime.now());
        var dataFilePath = Path.of(basePath, "data", currentDate, currentTime + ".json");
        var imageFilePath = Path.of(basePath, "images", currentDate, currentTime + ".jpeg");
        try {
            Files.createDirectories(dataFilePath.getParent());
            Files.createDirectories(imageFilePath.getParent());
        } catch (IOException e) {
            throw new HardwareException("Failed to create directory for data or row image dump", e);
        }
        try (var jsonGenerator = Json.createGenerator(Files.newOutputStream(dataFilePath));
             var imageOutputStream = Files.newOutputStream(imageFilePath)) {
            jsonGenerator.writeStartObject();
            jsonGenerator.write("temperature", readings.getTemperature());
            jsonGenerator.write("humidity", readings.getHumidity());
            jsonGenerator.write("pressure", readings.getPressure());
            jsonGenerator.write("light", readings.getLight());
            jsonGenerator.write("timestamp", readings.getTimestamp().toString());
            jsonGenerator.write("cameraMode", readings.getCameraMode().toString());
            jsonGenerator.writeEnd();
            LOG.info("Successfully wrote sensor data to file: " + dataFilePath);
            imageOutputStream.write(image);
            LOG.info("Successfully wrote raw image to file: " + imageFilePath);
        } catch (IOException e) {
            throw new HardwareException("Failed to write sensor data to file", e);
        }
    }
}
