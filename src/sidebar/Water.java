/*
 * This is the class that handles drawing the recoursive fractal, with perlin noise to look like water pouring
 */

package sidebar;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import processing.core.PApplet;
import processing.core.PVector;
import static util.Util.*;
import static main.GardenPanel.*;

public class Water {
    private float xSeed;
    private float ySeed;
    private PApplet pa;
    private PVector startPos;
    private int maxDepth = 5; // Control the depth of recursion

    public Water(float x, float y) {
        xSeed = random(10);
        ySeed = random(10);
        startPos = new PVector(x, y);
        pa = new PApplet();
    }

    public void updatePosition(double mouseX, double mouseY) {
        startPos.x = (float) mouseX;
        startPos.y = (float) mouseY;
    }

    public void drawWater(Graphics2D g2) {
        // Set the position for the Water (center of the drawing area)
        int centerX = (int) startPos.x;
        int centerY = (int) startPos.y;

        // Generate noise factor for the Water
        xSeed += 0.5;
        ySeed += 0.5;
        float noiseFactor = pa.noise(xSeed, ySeed);

        // Save the current transform
        AffineTransform at = g2.getTransform();

        // Translate the graphics context
        g2.translate(centerX, centerY);

        // Set color for the Water
        int red = 0;
        int green = 0;
        int blue = (int) (180 + (noiseFactor * 30)); // Adjusted blue component
        int alpha = (int) (70 + (noiseFactor * 40)); // Increased alpha range for more opacity
        g2.setColor(new Color(red, green, blue, alpha));

        // Draw the fractal splash
        drawFractalSplash(g2, 0, 0, noiseFactor * 30 + 10, maxDepth);

        g2.setTransform(at);
    }

    private void drawFractalSplash(Graphics2D g2, double x, double y, double size, int depth) {
        if (depth == 0) return;

        Ellipse2D.Double ellipse = new Ellipse2D.Double(x - size / 2, y - size / 2, size, size);
        g2.fill(ellipse);

        // Recursively draw smaller splashes
        int numSplashes = 6; // Number of smaller splashes
        for (int i = 0; i < numSplashes; i++) {
            double angle = Math.random() * Math.PI * 2;
            double radius = size * 0.5; // Reduce radius for smaller splashes
            double xOffset = Math.cos(angle) * radius;
            double yOffset = Math.sin(angle) * radius;
            double newSize = size * 0.5; // Reduce size for recursive splashes
            drawFractalSplash(g2, x + xOffset, y + yOffset, newSize, depth - 1);
        }
    }
}
