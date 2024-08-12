package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import processing.core.PVector;
import util.ImageLoader;

public class Instructions {
    private String instruction;
    private PVector pos;
    private BufferedImage img;
    private BufferedImage icon;

    public Instructions(int x, int y, String instruction, String icon){
        this.pos = new PVector(x, y);
        this.instruction = instruction;
        this.img = ImageLoader.loadImage("assets/instructionsBG.png");
        this.icon = ImageLoader.loadImage("assets/" + icon + ".png");
    }

    public void displayInstruction(Graphics2D g2){
        AffineTransform originalTransform = g2.getTransform();
        
        // Move to the position
        g2.translate(pos.x, pos.y);
        
        // Scale the image
        g2.scale(1, 1);

        // Draw the background image
        g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);

        // Reset the transformation to original
        g2.setTransform(originalTransform);
        
        // Move to the position again (after reset)
        g2.translate(pos.x, pos.y);
        
        // Draw the text
        g2.setFont(new Font("Arial", Font.PLAIN, 15));
        g2.setColor(Color.BLACK);

        // Calculate text position
        int stringWidth = g2.getFontMetrics().stringWidth(instruction);
        int stringHeight = g2.getFontMetrics().getHeight();
        int textX = -stringWidth / 2;
        int textY = stringHeight / 2 - 3;


        AffineTransform changeScale = g2.getTransform();
        g2.scale(0.5, 0.5);
        g2.drawImage(icon, -stringWidth - icon.getWidth() - (icon.getWidth() / 2), -icon.getHeight()/2, null);
        g2.setTransform(changeScale);
        g2.drawString(instruction, textX, textY);

        // Restore the original transformation at the end
        g2.setTransform(originalTransform);
    }
}
