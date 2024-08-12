package garden;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import util.ImageLoader;

public class Fence extends GardenObject{

	protected BufferedImage img2;

    public Fence(double x, double y, double s) {
		super(x, y, s);
		img = ImageLoader.loadImage("assets/fence.png");
		img2 = ImageLoader.loadImage("assets/carrotSign.png");
	}

	@Override
	public void update() {
		//this doesnt need to update	
	}

	@Override
    public void drawObject(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.scale(sca, sca);

		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);

		g2.translate(img.getWidth() / 2 + 20, img.getHeight() / 2);
		g2.drawImage(img2, -img2.getWidth() / 2, -img2.getHeight() / 2, null);

		g2.setTransform(transform);
	}

	@Override
	protected void setOutlineShape() {
		areaShape = new Rectangle2D.Double(-img.getWidth() / 2, -img.getHeight() / 2, img.getWidth(), img.getHeight());
	}
}
