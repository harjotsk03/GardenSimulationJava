package sidebar;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import util.ImageLoader;

public class Corn extends SidebarTool{

    public Corn(double x, double y, double s) {
        super(x, y, s);
        img = ImageLoader.loadImage("assets/corn.png");
    }

    @Override
    public void drawObject(Graphics2D g2) {
        AffineTransform transform = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.scale(sca, sca);

		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);

		g2.setTransform(transform);
    }    
    
    @Override
    protected void setOutlineShape() {
        areaShape = new Ellipse2D.Double(-img.getWidth() / 2, -img.getHeight() / 2, img.getWidth(), img.getHeight());
    }   
}
