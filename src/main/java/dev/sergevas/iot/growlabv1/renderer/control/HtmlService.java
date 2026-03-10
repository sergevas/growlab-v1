package dev.sergevas.iot.growlabv1.renderer.control;

import dev.sergevas.iot.growlabv1.renderer.control.entity.SensorReadings;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.FileWriter;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class HtmlService {

    private final Configuration freemarker;

    public HtmlService(Class<?> resourceLoaderClass, String basePackagePath) throws Exception {

        freemarker = new Configuration(Configuration.VERSION_2_3_32);
        freemarker.setClassForTemplateLoading(resourceLoaderClass, basePackagePath);
        freemarker.setDefaultEncoding("UTF-8");
    }

    public void generate(String outputDir, SensorReadings r) throws Exception {

        Template template = freemarker.getTemplate("index.ftl");

        Map<String, Object> model = new HashMap<>();

        model.put("time", r.getTime());

        model.put("temperature",
                r.getTemperature() != null
                        ? String.format("%5.2f°C", r.getTemperature())
                        : "N/A");

        model.put("humidity",
                r.getHumidity() != null
                        ? String.format("%05.2f%%", r.getHumidity())
                        : "N/A");

        model.put("pressure",
                r.getPressure() != null
                        ? String.format("%05.2f hPa (%d mmHg)",
                        r.getPressure(), r.getPressureMmhg())
                        : "N/A");

        model.put("light",
                r.getLight() != null
                        ? String.format("%5.2f lx", r.getLight())
                        : "N/A");

        model.put("camera_mode",
                r.getCameraMode() != null
                        ? r.getCameraMode()
                        : "N/A");

        model.put("uid", Instant.now().toEpochMilli());

        try (FileWriter writer = new FileWriter(outputDir + "/index.html")) {
            template.process(model, writer);
        }
    }
}
