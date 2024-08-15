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

public class Alert {

    private PVector pos;
    private BufferedImage img;
    private double scaleX;
    private double scaleY;
    private String text;
    private long creationTime; // Time when the alert was created
    private static final long DISPLAY_DURATION_MS = 5000; // 5 seconds
    private boolean active; // State of the alert
    private Font customFont;

    public Alert(int x, int y, String text) {
        loadCustomFont();
        this.pos = new PVector(x, y);
        this.img = ImageLoader.loadImage("assets/instructionsBG.png");
        this.scaleX = 1;
        this.scaleY = 0.7;
        this.text = text;
        this.creationTime = System.currentTimeMillis(); // Set the creation time
        this.active = true; // Alert is active initially
    }

    public boolean isActive() {
        // Check if the alert should still be active
        if (System.currentTimeMillis() - creationTime > DISPLAY_DURATION_MS) {
            this.active = false; // Mark alert as inactive
        }
        return this.active;
    }

    public void drawButton(Graphics2D g2) {
        if (!isActive()) {
            // Alert is no longer active, do not draw
            return;
        }

        // Save the original transform and font state
        AffineTransform originalTransform = g2.getTransform();
        Font originalFont = g2.getFont();
        
        g2.translate(this.pos.x, this.pos.y);
        g2.scale(scaleX, scaleY);
        g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        
        g2.setTransform(originalTransform);
        if (this.text.equals("+") || this.text.equals("-")) {
            g2.setFont(customFont.deriveFont(Font.PLAIN, 20));
        } else {
            g2.setFont(customFont.deriveFont(Font.PLAIN, 20));
        }
        g2.setColor(Color.BLACK);

        // Calculate text position
        int stringWidth = g2.getFontMetrics().stringWidth(this.text);
        int stringHeight = g2.getFontMetrics().getHeight();
        int textX = (int) (this.pos.x + (img.getWidth() * scaleX) / 2 - stringWidth / 2);
        int textY = 0;
        if (this.text.equals("+") || this.text.equals("-")) {
            textY = (int) (this.pos.y + (img.getHeight() * scaleY) / 2 + stringHeight / 2 - 6);
        } else {
            textY = (int) (this.pos.y + (img.getHeight() * scaleY) / 2 + stringHeight / 2 - 3);
        }

        // Draw the text
        g2.drawString(this.text, textX, textY);
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
