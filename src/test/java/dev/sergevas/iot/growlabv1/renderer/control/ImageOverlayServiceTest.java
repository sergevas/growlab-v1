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
        Font font = Font.createFont(
                Font.TRUETYPE_FONT,
                Objects.requireNonNull(getClass().getResourceAsStream("/fonts/NotoSans-Regular.ttf"))
        ).deriveFont(52f);

        ImageOverlayService renderer =
                new ImageOverlayService(
                        font,
                        Color.LIGHT_GRAY
                );

        renderer.render(Objects.requireNonNull(getClass().getResourceAsStream("/image/response.jpg")), "response_mod.jpg",
                Arrays.asList("Окружающая среда Луковки", "Температура: 22.4°C", "Относительная влажность: 55%",
                        "Давление: 101300 Па", "Количество света: 420 лк"));
    }
}