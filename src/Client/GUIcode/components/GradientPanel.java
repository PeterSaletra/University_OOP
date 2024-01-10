package src.Client.GUIcode.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

public class GradientPanel extends JLayeredPane {
    private Color color1;
    private Color color2;

    public GradientPanel(Color color) {
        this.color1 = color;
        this.color2 = color.darker();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, this.color1, 0, h, this.color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

    public void setBackgroundColors(Color color) {
        this.color1 = color;
        this.color2 = color.darker();
        repaint();
    }
}
