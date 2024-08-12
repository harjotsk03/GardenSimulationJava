package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Font;
import util.ImageLoader;

public class SellingScreen extends ScreenClass{

    protected BufferedImage img;

    private int carrotReady;
    private int lettuceReady;
    private int cornReady;
    private int tomatoReady;

    private String text;

    public SellingScreen(int screenW, int screenH, int carrotReady, int lettuceReady, int cornReady, int tomatoReady) {
        super(screenW, screenH);
        img = ImageLoader.loadImage("assets/sellingScreen.png");
        this.carrotReady = carrotReady;
        this.lettuceReady = lettuceReady;
        this.cornReady = cornReady;
        this.tomatoReady = tomatoReady;
    }

    @Override
    public void drawScreen(Graphics2D g2) {
        g2.drawImage(img, 0, 0, screenSize.width, screenSize.height, null);

        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, screenSize.width, screenSize.height);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.WHITE);

        g2.drawString("Carrots: " + carrotReady, 50, 50);
        
        g2.drawString("Lettuce: " + lettuceReady, 250, 50);
        g2.drawString("Tomatos: " + tomatoReady, 450, 50);
        g2.drawString("Corn: " + cornReady, 650, 50);
    }

    @Override
    public boolean clicked(double x, double y) {
        return x > 500 && x < 800 && y > 600 && y < 750;
    }
}
