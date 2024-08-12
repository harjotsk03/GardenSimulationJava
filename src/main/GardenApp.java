package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class GardenApp extends JFrame{
	private static final long serialVersionUID = 1L;

	public GardenApp(String title) {
		super(title);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GardenPanel bpnl = new GardenPanel(this);
		this.add(bpnl);
		this.pack();
		// this.setLocationRelativeTo(null);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        // Select the desired monitor (e.g., the second monitor)
        if (gs.length > 1) {
            GraphicsDevice gd = gs[1]; // Index 1 for the second monitor

            // Get the bounds of the selected monitor
            Rectangle bounds = gd.getDefaultConfiguration().getBounds();

            // Set the location of the frame to the top-left corner of the selected monitor
            this.setLocation(bounds.x, bounds.y);
        } else {
            // If there's only one monitor, just center the frame
            this.setLocationRelativeTo(null);
        }
		this.setVisible(true);
	}
	
	public static void main (String[] args){
		new GardenApp("Garden Simulator");
		
	}
}