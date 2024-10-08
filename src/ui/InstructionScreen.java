package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import util.ImageLoader;

public class InstructionScreen extends ScreenClass{
    
    protected BufferedImage img;

    public InstructionScreen(int screenW, int screenH){
        super(screenW, screenH);
        img = ImageLoader.loadImage("assets/instructionsScreen.png");
    }

    @Override
    public void drawScreen(Graphics2D g2){
        g2.drawImage(img, 0, 0, screenSize.width, screenSize.height, null);
    }

    @Override
    public boolean clicked(double x, double y){
		boolean clicked = false;
		
		return clicked;
	}
}
