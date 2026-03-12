package dev.sergevas.iot.growlabv1.renderer.control;

import dev.sergevas.iot.growlabv1.renderer.control.entity.SensorReadings;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

class HtmlServiceTest {

    @Test
    void givenSensorReadings_whenGenerate_thenShouldCreateHtmlSuccessfully() throws Exception {
        HtmlService htmlService = new HtmlService();
        SensorReadings sensorReadings = new SensorReadings(
                Instant.now(),
                22.4,
                101300.0,
                760,
                55.0,
                420.0,
                "photo"
        );
        var resourceDirectory = Paths.get("src", "main", "resources", "html", "onion.html");
        Files.writeString(resourceDirectory, htmlService.generateHTML(sensorReadings), CREATE, TRUNCATE_EXISTING);
    }
}