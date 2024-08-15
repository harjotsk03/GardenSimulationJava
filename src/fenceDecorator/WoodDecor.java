/*
 * This is the class that handles creating the bags for the decorator pattern
 */

package fenceDecorator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import util.ImageLoader;

public class WoodDecor extends FenceDecor{

    protected BufferedImage img;

    public WoodDecor(int x, int y, FenceDecorInterface baseFence){
        super(x, y, baseFence);
        this.img = ImageLoader.loadImage("assets/decorWood.png");
    }

    public void showFence(Graphics2D g2){
        super.showFence(g2);
        addWood(g2);
    }

    private void addWood(Graphics2D g2){
        AffineTransform transform = g2.getTransform();
		g2.translate(pos.x, pos.y);

		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);

		g2.setTransform(transform);
    }
}
