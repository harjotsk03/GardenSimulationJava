package sidebar;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;

import processing.core.PVector;
import util.ImageLoader;


public abstract class SidebarTool {
    protected PVector pos;
	protected double sca;
	protected BufferedImage img;
	protected Area outline;
	protected Ellipse2D.Double areaShape;
	protected Boolean mouseFollowing = false;
    
    public SidebarTool(double x, double y, double s){
        pos = new PVector((float) x,(float) y);
		sca = s;
        img = ImageLoader.loadImage("assets/dirt.png");

		setOutlineShape();
		setOutline();
    }

	protected abstract void setOutlineShape();

    public abstract void drawObject(Graphics2D g2);

    public PVector getPos() {
		return pos;
	}

	private void setOutline(){
		outline = new Area(areaShape);
	}

	public boolean getMouseFollowing(){
		return mouseFollowing;
	}

	public void setMouseFollowing(boolean statement){
		mouseFollowing = statement;
	}

    public void setPos(double mouseX, double mouseY){
        this.pos.x = (float) mouseX;
        this.pos.y = (float) mouseY;
    }
	
	public double getWidth() {
		return img.getWidth() * sca;
	}

    public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if (x > (pos.x - ((double) img.getWidth()) / 2 * sca) && x < (pos.x + ((double) img.getWidth())/2*sca) && y > (pos.y - ((double) img.getHeight())/2*sca) && y < (pos.y + ((double) img.getHeight())/2*sca)) 
			clicked = true;
		
		return clicked;
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
