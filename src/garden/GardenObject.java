package garden;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import processing.core.PVector;
import sidebar.SidebarTool;
import util.ImageLoader;

public abstract class GardenObject {
    protected PVector pos;
	protected double sca;
	protected BufferedImage img;
	protected Area outline;
	protected Rectangle2D.Double areaShape;

	public GardenObject(double x, double y, double s) {
		pos = new PVector((float) x, (float) y);
		sca = s;
		img = ImageLoader.loadImage("assets/dirt.png");

		setOutlineShape();
		setOutline();
	}

	protected abstract void setOutlineShape();

	private void setOutline(){
		outline = new Area(areaShape);
	}

    public abstract void drawObject(Graphics2D g2);
	public abstract void update();

    public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if (x > (pos.x - ((double) img.getWidth()) / 2 * sca) && x < (pos.x + ((double) img.getWidth())/2*sca) && y > (pos.y - ((double) img.getHeight())/2*sca) && y < (pos.y + ((double) img.getHeight())/2*sca)) 
			clicked = true;
		
		return clicked;
	}

    public PVector getPos() {
		return pos;
	}
	
	
	public double getWidth() {
		return img.getWidth() * sca;
	}

	public boolean isColliding(SidebarTool tool) {
		return (getOutline().intersects(tool.getBoundingBox()) &&
			tool.getOutline().intersects(getBoundingBox()) );
	}

	public Rectangle2D getBoundingBox() {
		return getOutline().getBounds2D();
	}

	public Shape getOutline() {
		AffineTransform at = new AffineTransform();
		at.translate(pos.x, pos.y);
		at.scale(sca, sca);
		return at.createTransformedShape(outline);
	}
}
