/*
 * Class for drawing the background
 */

package garden;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.GardenPanel;
import util.ImageLoader;

public class Garden {
	private BufferedImage img;

	public Garden(String file) {
		img = ImageLoader.loadImage(file);
	}

	public void drawGarden(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(0, 0);
		g2.scale(1, 1);
		g2.drawImage(img, 0, 0, GardenPanel.W_WIDTH, GardenPanel.W_HEIGHT, null);
		
		g2.setTransform(at);
	}

}
