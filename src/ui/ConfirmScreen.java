package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import util.ImageLoader;

public class ConfirmScreen extends ScreenClass{
    
    protected BufferedImage img;

    public ConfirmScreen(int screenW, int screenH){
        super(screenW, screenH);
        img = ImageLoader.loadImage("assets/areYouSureScreen.png");
    }

    @Override
    public void drawScreen(Graphics2D g2){
        g2.drawImage(img, 0, 0, screenSize.width, screenSize.height, null);
        g2.setColor(Color.red);
        g2.fillRect(800, 600, (int) screenSize.getWidth(), 750);
    }

    @Override
    public boolean confirmClick(double x, double y){
		boolean clicked = false;
		
        if (x > 800 && x < screenSize.getWidth() && y > 600 && y < 750) {
			clicked = true;
        }
		
		return clicked;
	}

    @Override
    public boolean cancelClick(double x, double y) {
        boolean clicked = false;
		
        if (x > 0 && x < 10 && y > 0 && y < 10) {
			clicked = true;
        }
		
		return clicked;
    }
}
