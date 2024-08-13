package sidebar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import util.ImageLoader;

public class SimpleSidebar {

    private double xPos;
	private double yPos;
	private double sca;
    private BufferedImage img;

    public SimpleSidebar(double x, double y, double s){
        xPos = x;
		yPos = y;
		sca = s;
        img = ImageLoader.loadImage("assets/sidebar.png");
    }

    public void drawSidebar(Graphics2D g2){
        AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);

        g2.scale(sca*1.5, sca*2.25);
		
        g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);

		g2.setTransform(transform);
    }
}
