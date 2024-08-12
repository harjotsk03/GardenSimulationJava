package garden;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import processing.core.PApplet;
import processing.core.PVector;

import static util.Util.*;
import static main.GardenPanel.*;

public class Sparkle {
	private float xStart;
	private float xSeed;
	private float ySeed;
	private PApplet pa;
    private PVector startPos;

	public  Sparkle(float x, float y) {
		xStart = random(10);
        startPos = new PVector((int) x, (int) y);
		xSeed = xStart;
		xSeed = random(10);
		pa = new PApplet();
	}

	public void drawSparkle(Graphics2D g2) {
        // Set the position for the sparkle (center of the drawing area)
        int centerX = (int) startPos.x;
        int centerY = (int) startPos.y;

        // Generate noise factor for the sparkle
        xSeed += 0.5;
        ySeed += 0.5;
        float noiseFactor = pa.noise(xSeed, ySeed);

        // Save the current transform
        AffineTransform at = g2.getTransform();

        // Translate and rotate the graphics context
        g2.translate(centerX, centerY);
        g2.rotate(noiseFactor * radians(360));

        // Set color for the sparkle
        int red = (int) (200 + (noiseFactor * 55));
        int green = (int) (200 + (noiseFactor * 55));
        int blue = (int) (200 + (noiseFactor * 55));
        int alpha = (int) (40 + (noiseFactor * 105));
        g2.setColor(new Color(red, green, blue, alpha));

        drawSparkleShape(g2, 0, 0, noiseFactor * 10 + 10);

        g2.setTransform(at);
    }

    private void drawSparkleShape(Graphics2D g2, double x, double y, double size) {
        int rays = 5;
        double angle = Math.PI / rays;
        for (int i = 0; i < rays * 2; i++) {
            double radius = (i & 1) == 0 ? size : size / 2;
            double xOffset = Math.cos(i * angle) * radius;
            double yOffset = Math.sin(i * angle) * radius;
            g2.drawLine((int)x, (int)y, (int)(x + xOffset), (int)(y + yOffset));
        }
    }
}
