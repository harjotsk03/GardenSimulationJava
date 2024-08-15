package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Font;
import util.ImageLoader;

public class EndingScreen extends ScreenClass{

    protected BufferedImage img;

    public EndingScreen(int screenW, int screenH) {
        super(screenW, screenH);
        img = ImageLoader.loadImage("assets/endScreen.png");
    }

    @Override
    public void drawScreen(Graphics2D g2) {
        g2.drawImage(img, 0, 0, screenSize.width, screenSize.height, null);
    }

    @Override
    public boolean clicked(double x, double y) {
        return false;
    }

}
