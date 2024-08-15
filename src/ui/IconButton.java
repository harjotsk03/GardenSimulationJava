/* 
 * This is a class to create custom buttons I used throughout the application
 */

package ui;

import processing.core.PVector;
import util.ImageLoader;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IconButton implements elementInterface{

    private PVector pos;
    private BufferedImage img;
    private double scaleX;
    private double scaleY;
    private String text;
    private Font customFont;

    public IconButton(int x, int y, String text) {
        loadCustomFont();
        this.pos = new PVector(x, y);
        this.img = ImageLoader.loadImage("assets/instructionsBG.png");
        this.scaleX = 0.23;
        this.scaleY = 0.3;

        if(text == "Sell Crops"){
            this.scaleX = 0.2;
            this.scaleY = 0.3;
        }else if(text == "+" || text == "-"){
            this.scaleX = 0.1;
            this.scaleY = 0.3;
        }else if(text == "Play Again" || text == "Continue"){
            this.scaleX = 0.4;
            this.scaleY = 0.5;
        }else if(text == "Help"){
            this.scaleX = 0.1;
            this.scaleY = 0.3;
        }
        this.text = text;
    }

    public void drawButton(Graphics2D g2) {
        // Save the original transform and font state
        AffineTransform originalTransform = g2.getTransform();
        Font originalFont = g2.getFont();
        
        // Apply transformations for the image
        g2.translate(this.pos.x, this.pos.y);
        g2.scale(scaleX, scaleY);
        g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        
        // Restore the font and transform state before drawing text
        g2.setTransform(originalTransform);
        if(this.text == "+" || this.text == "-"){
            g2.setFont(customFont.deriveFont(Font.PLAIN, 20)); 
        }else{
            g2.setFont(customFont.deriveFont(Font.PLAIN, 14));
        }
        g2.setColor(Color.BLACK);

        // Calculate text position
        int stringWidth = g2.getFontMetrics().stringWidth(this.text);
        int stringHeight = g2.getFontMetrics().getHeight();
        int textX = (int) (this.pos.x + (img.getWidth() * scaleX) / 2 - stringWidth / 2);
        int textY = 0;
        if(this.text == "+" || this.text == "-"){
            textY = (int) (this.pos.y + (img.getHeight() * scaleY) / 2 + stringHeight / 2 - 6);
        }else{
            textY = (int) (this.pos.y + (img.getHeight() * scaleY) / 2 + stringHeight / 2 - 3);
        }

        // Draw the text
        g2.drawString(this.text, textX, textY);
    }


    @Override
    public boolean clicked(double x, double y) {
        int scaledWidth = (int) (img.getWidth() * scaleX);
        int scaledHeight = (int) (img.getHeight() * scaleY);
        
        double left = this.pos.x;
        double right = this.pos.x + scaledWidth;
        double top = this.pos.y;
        double bottom = this.pos.y + scaledHeight;

        return x > left && x < right && y > top && y < bottom;
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
