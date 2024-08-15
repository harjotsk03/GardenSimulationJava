package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.awt.Font;
import util.ImageLoader;

public class ScreenClass implements elementInterface{
    
    protected Dimension screenSize;
    protected BufferedImage img;

    public ScreenClass (int screenW, int screenH){
        this.screenSize = new Dimension(screenW, screenH);
    }

    public void drawScreen(Graphics2D g2){
    }

    @Override
    public boolean clicked(double x, double y){
		boolean clicked = false;
		return clicked;
	}

    public boolean confirmClick(double mouseX, double mouseY) {
        return false;
    }

    public boolean cancelClick(double mouseX, double mouseY) {
        return false;
    }

    public boolean clickedPlayAgain(double mouseX, double mouseY) {
        return false;        
    }

    public boolean clickedContinue(double mouseX, double mouseY) {
        return false;
    }
}
