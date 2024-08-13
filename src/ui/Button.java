package ui;

import processing.core.PVector;
import util.ImageLoader;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Button implements elementInterface{

    private PVector pos;
    private int state = 1; // 1 for pause, 2 for play
    protected BufferedImage img;

    public Button(int x, int y) {
        this.pos = new PVector(x, y);
        update(); // Load the initial image based on the default state
    }

    public int getState() {
        return state;
    }

    public void update() {
        if (this.state == 1) {
            img = ImageLoader.loadImage("assets/pauseButton.png");
        } else if (this.state == 2) {
            img = ImageLoader.loadImage("assets/playButton.png");
        }
    }

    public void drawButton(Graphics2D g2) {
        g2.drawImage(img, (int) this.pos.x, (int) this.pos.y, img.getWidth(), img.getHeight(), null);
    }

    @Override
    public boolean clicked(double x, double y) {
        return x > this.pos.x && x < this.pos.x + img.getWidth() &&
               y > this.pos.y && y < this.pos.y + img.getHeight();
    }

    public void setState(int value) {
        this.state = value;
        update(); // Update the image based on the new state
    }
}
