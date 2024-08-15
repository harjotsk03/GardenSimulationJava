package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

import processing.core.PVector;
import util.ImageLoader;

public class Instructions {
    private String instruction;
    private PVector pos;
    private BufferedImage img;
    private BufferedImage icon;
    private Font customFont;

    public Instructions(int x, int y, String instruction, String icon){
        loadCustomFont();
        this.pos = new PVector(x, y);
        this.instruction = instruction;
        this.img = ImageLoader.loadImage("assets/instructionsBG.png");
        this.icon = ImageLoader.loadImage("assets/" + icon + ".png");
    }

    public void displayInstruction(Graphics2D g2){
    AffineTransform originalTransform = g2.getTransform();
    
    // Move to the position
    g2.translate(pos.x, pos.y);

    if(instruction == "Carrot\nValue: $0.80/each\nGrowth Time: 3 Days"){
        g2.scale(0.5, 1.2);
    }else if(instruction == "Tomato\nValue: $1.60/each\nGrowth Time: 4 Days"){
        g2.scale(0.5, 1.2);
    }else if(instruction == "Corn\nValue: $2.10/each\nGrowth Time: 5 Days"){
        g2.scale(0.5, 1.2);
    }else if(instruction == "Lettuce\nValue: $1.00/each\nGrowth Time: 3 Days"){
        g2.scale(0.5, 1.2);
    }
    else{
            g2.scale(1.25, 1);
    }
    

    // Draw the background image
    g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);

    // Reset the transformation to original
    g2.setTransform(originalTransform);
    
    // Move to the position again (after reset)
    g2.translate(pos.x, pos.y);
    
    // Draw the text
    g2.setFont(customFont.deriveFont(Font.PLAIN, 20));
    g2.setColor(Color.BLACK);

    FontMetrics fm = g2.getFontMetrics();
    String[] lines = instruction.split("\n");
    
    // Calculate starting Y position
    int textY = fm.getAscent() - ( 10 * lines.length);
    
    // Draw icon
    AffineTransform changeScale = g2.getTransform();
    g2.scale(0.5, 0.5);
    // g2.drawImage(icon, -fm.stringWidth(instruction) - icon.getWidth() - (icon.getWidth() / 2), -icon.getHeight() / 2, null);
    g2.setTransform(changeScale);

    // Draw each line of the text
    for (String line : lines) {
        int textX = -fm.stringWidth(line) / 2;
        g2.drawString(line, textX, textY);
        if(instruction == "Carrot\nValue: $0.80/each\nGrowth Time: 3 Days"){
            textY += fm.getHeight() - 5;
        }else if(instruction == "Carrot\nValue: $0.80/each\nGrowth Time: 3 Days"){
            textY += fm.getHeight() - 5;
        }else if(instruction == "Carrot\nValue: $0.80/each\nGrowth Time: 3 Days"){
            textY += fm.getHeight() - 5;
        }else if(instruction == "Carrot\nValue: $0.80/each\nGrowth Time: 3 Days"){
            textY += fm.getHeight() - 5;
        }
        else{
            textY += fm.getHeight();
        }
        
    }

    // Restore the original transformation at the end
    g2.setTransform(originalTransform);
}

    private void loadCustomFont() {
            try {
                // Load the custom font from file
                customFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/IrishGrover-Regular.ttf")).deriveFont(40f);
                
                // Register the font with the GraphicsEnvironment
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);
                
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }



}
