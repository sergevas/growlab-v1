package dev.sergevas.iot.growlabv1.renderer.control;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

public class TextLayoutEngine {

    public static List<TextLayout> layoutText(
            String text,
            Font font,
            FontRenderContext frc,
            float maxWidth
    ) {

        AttributedString attributed = new AttributedString(text);
        attributed.addAttribute(TextAttribute.FONT, font);

        AttributedCharacterIterator it = attributed.getIterator();

        LineBreakMeasurer measurer =
                new LineBreakMeasurer(it, frc);

        List<TextLayout> layouts = new ArrayList<>();

        while (measurer.getPosition() < it.getEndIndex()) {
            layouts.add(measurer.nextLayout(maxWidth));
        }

        return layouts;
    }
}
