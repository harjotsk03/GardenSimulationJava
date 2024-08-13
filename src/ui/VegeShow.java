package ui;
import processing.core.PVector;
import util.ImageLoader;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class VegeShow implements elementInterface{

    private double xPos;
	private double yPos;
	private double sca;
    private BufferedImage img;

    public VegeShow (int x, int y, int scale){
        this.xPos = x;
        this.yPos = y;
        this.sca = scale;
        this.img = ImageLoader.loadImage("assets/uiArea.png");
    }

    public void drawButton(Graphics2D g2) {
        AffineTransform originalTransform = g2.getTransform();        
        // Apply transformations for the image
        g2.translate(xPos, yPos);
        g2.scale(0.8, 1);
        g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        
        // Restore the font and transform state before drawing text
        g2.setTransform(originalTransform);
    }

    @Override
    public boolean clicked(double x, double y) {
        return false;
    }
    
}
