package src.Client.GUIcode.components;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Placeholder extends RoundedTextField {
    private String placeholder;
    private final int TEXT_OFFSET = 20;
    public RoundedTextField finalPlaceholderComponent ;

    public Placeholder(String text) {
        this.setPlaceholder("Nickname");

        this.finalPlaceholderComponent = this;
    }
    public String getPlaceholder() {
        return placeholder;
    }
    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.isEmpty() || !getText().isEmpty()) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getBaseline(this.getWidth(), this.getHeight())+TEXT_OFFSET , getBaseline(this.getWidth(), this.getHeight()));
    }
    public void setPlaceholder(final String s) {
        placeholder = s;
    }

}
