package dev.sergevas.iot.growlabv1.renderer.control;

import dev.sergevas.iot.growlabv1.shared.controller.ExceptionUtils;
import dev.sergevas.iot.growlabv1.shared.exception.SensorException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class ImageOverlayService {

    private static final Logger LOG = Logger.getLogger(ImageOverlayService.class.getName());

    private final Font font;
    private final Color textColor;

    public ImageOverlayService() {
        this(Color.WHITE);
    }

    public ImageOverlayService(Color textColor) {
        try {
//            this.font = Font.createFont(Font.TRUETYPE_FONT,
//                    Objects.requireNonNull(ImageOverlayService.class.getClassLoader()
//                            .getResourceAsStream("fonts/NotoSans-Regular.ttf"))
//            ).deriveFont(52f);
            this.font = new Font("SansSerif", Font.PLAIN, 52);
        } catch (Exception e) {
            LOG.severe(String.format("Unable to load font for image overlay: %s", ExceptionUtils.getStackTrace(e)));
            throw new SensorException("Unable to create image font", e);
        }
        this.textColor = textColor;
    }

    public BufferedImage render(byte[] inputImageBytes, List<String> message) {
        BufferedImage img;
        try {
            img = ImageIO.read(new ByteArrayInputStream(inputImageBytes));
            Graphics2D g = img.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            FontRenderContext frc = g.getFontRenderContext();
            float maxWidth = img.getWidth() * 0.4f;
            List<TextLayout> lines = TextLayoutEngine.layoutText(message, font, frc, maxWidth);
            float lineHeight = font.getSize2D() * 1.3f;
            int x = 40;
            int y = 60;
            int boxWidth = (int) maxWidth;
            int boxHeight = (int) (lines.size() * lineHeight) + 40;
            BufferedImage region = img.getSubimage(x, y, boxWidth, boxHeight);
            BufferedImage blurred = BlurRenderer.blur(region);
            g.drawImage(blurred, x, y, null);
            g.setColor(new Color(0, 0, 0, 120));
            g.fillRoundRect(x, y, boxWidth, boxHeight, 20, 20);
            g.setColor(textColor);
            g.drawRoundRect(x, y, boxWidth, boxHeight, 20, 20);
            g.setColor(textColor);
            float textY = y + 30;
            for (TextLayout layout : lines) {
                textY += layout.getAscent();
                layout.draw(g, x + 20, textY);
                textY += layout.getDescent() + layout.getLeading();
            }
            g.dispose();
            return img;
        } catch (Exception e) {
            throw new SensorException("Unable to render image overlay", e);
        }
    }
}
