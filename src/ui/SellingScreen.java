package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Font;
import util.ImageLoader;

public class SellingScreen extends ScreenClass{

    protected BufferedImage img;

    public SellingScreen(int screenW, int screenH) {
        super(screenW, screenH);
        img = ImageLoader.loadImage("assets/sellingScreen.png");
    }

    @Override
    public void drawScreen(Graphics2D g2) {
        g2.drawImage(img, 0, 0, screenSize.width, screenSize.height, null);
    }

    @Override
    public boolean clicked(double x, double y) {
        return x > 510 && x < 760 && y > 610 && y < 700;
    }
}
