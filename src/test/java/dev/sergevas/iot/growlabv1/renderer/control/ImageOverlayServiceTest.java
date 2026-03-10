package dev.sergevas.iot.growlabv1.renderer.control;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;

class ImageOverlayServiceTest {

    @Test
    void givenImage_whenRender_thenShouldReturnSuccessfully() throws Exception {
        Font font = Font.createFont(
                Font.TRUETYPE_FONT,
                new File("fonts/NotoSans-Regular.ttf")
        ).deriveFont(28f);

        ImageOverlayService renderer =
                new ImageOverlayService(
                        font,
                        Color.WHITE
                );

        renderer.render("response.jpg", "response_mod.jpg", "#growlab\nTemperature: 22.4°C\nHumidity: 55%\nPressure: 1013 hPa\nLight: 420 lx");
    }
}