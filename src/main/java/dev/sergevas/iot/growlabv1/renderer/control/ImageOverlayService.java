package dev.sergevas.iot.growlabv1.renderer.control;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class ImageOverlayService {

    private final Font font;
    private final Color textColor;

    public ImageOverlayService(Font font, Color textColor) {
        this.font = font;
        this.textColor = textColor;
    }

    public void render(InputStream input, String output, List<String> message) throws Exception {

        BufferedImage img = ImageIO.read(input);

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

        g.setColor(Color.WHITE);
        g.drawRoundRect(x, y, boxWidth, boxHeight, 20, 20);

        g.setColor(textColor);

        float textY = y + 30;

        for (TextLayout layout : lines) {

            textY += layout.getAscent();

            layout.draw(g, x + 20, textY);

            textY += layout.getDescent() + layout.getLeading();
        }

        g.dispose();

        ImageIO.write(img, "jpg", new File(output));
    }
}
