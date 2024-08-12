package fenceDecorator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import processing.core.PVector;
import util.ImageLoader;

public class SimpleFence implements FenceDecorInterface{

    protected BufferedImage img;
    protected PVector pos;

    public SimpleFence(int x, int y){
        this.img = ImageLoader.loadImage("assets/decorFence.png");
        this.pos = new PVector(x, y);
    }

    @Override
    public void showFence(Graphics2D g2){
        AffineTransform transform = g2.getTransform();
		g2.translate(pos.x, pos.y);

		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);

		g2.setTransform(transform);
    }
}
