package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import util.ImageLoader;

public class ConfirmScreen {
    
    private Dimension screenSize;
    protected BufferedImage img;

    public ConfirmScreen(int screenW, int screenH){
        this.screenSize = new Dimension(screenW, screenH);
        img = ImageLoader.loadImage("assets/areYouSureScreen.png");
    }

    public void drawInstructionScreen(Graphics2D g2){
        g2.drawImage(img, 0, 0, screenSize.width, screenSize.height, null);
        g2.setColor(Color.red);
        g2.fillRect(800, 600, (int) screenSize.getWidth(), 750);
    }

    public boolean confirmClick(double x, double y){
		boolean clicked = false;
		
        if (x > 800 && x < screenSize.getWidth() && y > 600 && y < 750) {
			clicked = true;
        }
		
		return clicked;
	}

    public boolean cancelClick(double x, double y) {
        boolean clicked = false;
		
        if (x > 0 && x < 10 && y > 0 && y < 10) {
			clicked = true;
        }
		
		return clicked;
    }
}
