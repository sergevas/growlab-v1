package dev.sergevas.iot.growlabv1.renderer.control;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

@Disabled
class ImageOverlayServiceTest {

    @Test
    void givenImage_whenRender_thenShouldReturnSuccessfully() throws Exception {
        ImageOverlayService renderer = new ImageOverlayService(Color.WHITE);
        renderer.render(Objects.requireNonNull(getClass().getResourceAsStream("/image/response.jpg").readAllBytes()),
                Arrays.asList("Луковка онлайн", "Температура: 22.4°C", "Относительная влажность: 55%",
                        "Давление: 101300 Па", "Количество света: 420 лк"));
    }
}