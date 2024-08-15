/*
 * This is the base fence decor class that starts our decoration
 */

package fenceDecorator;

import java.awt.Graphics2D;

import processing.core.PVector;

public class FenceDecor implements FenceDecorInterface{
    protected FenceDecorInterface baseFence;
    protected PVector pos;

    public FenceDecor(int x, int y, FenceDecorInterface baseFence){
        this.pos = new PVector(x, y);
        this.baseFence = baseFence;
    }

    public void showFence(Graphics2D g2){
        baseFence.showFence(g2);
    }
}
