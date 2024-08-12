package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.*;

public class Sun {

	public void drawSun(Graphics2D g2, int centerX, int centerY, int radius, int depth) {
        if (depth == 0) {
            return;
        }

        g2.setColor(new Color(255, 255, 0, 255 / depth));
        g2.setStroke(new BasicStroke(2)); // Stroke width
        g2.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        g2.setColor(new Color(255, 255, 10, 255 / depth));
        g2.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        int newRadius = radius / 2;

        drawSun(g2, centerX, centerY, newRadius, depth - 1);
    }


}
