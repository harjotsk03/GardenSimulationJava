package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import util.ImageLoader;

public class IntoScreen extends ScreenClass{
    
    protected BufferedImage img;

    public IntoScreen(int screenW, int screenH){
        super(screenW, screenH);
        img = ImageLoader.loadImage("assets/IntroScreen.png");
    }

    @Override
    public void drawScreen(Graphics2D g2){
        g2.drawImage(img, 0, 0, screenSize.width, screenSize.height, null);
    }

    @Override
    public boolean clicked(double x, double y){
		boolean clicked = false;
		
        if (x > 500 && x < 800 && y > 600 && y < 750) {
			clicked = true;
        }    
		
		return clicked;
	}
}
