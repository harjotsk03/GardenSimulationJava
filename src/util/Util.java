package util;

import java.util.ArrayList;

import garden.Dirt;
import garden.Fence;

public class Util {
	
	public static double random(double low, double high){
		return low + Math.random() * (high - low);
		}
	
	public static double dist(double x1, double y1, double x2, double y2){
		double distance = Math.abs(x1-x2)+Math.abs(y1-y2)-Math.min(Math.abs(x1-x2), Math.abs(y1-y2));
		return distance;
	}
	
	public static float random(float low, float high) {
		return low + (float) Math.random() * (high - low);
	}
	
	
	public static float random(float high) {
		return (float) Math.random() * high;
	}

	
	public static double radians(double angle){
		return angle/180*Math.PI;		
	}
	
	
	public static float radians(float angle){
		return angle/180*(float)Math.PI;		
	}
	
	public static void generateGarden(int startX, int startY, ArrayList<Dirt> DirtArr, int fenceX, int fenceY, ArrayList<Fence> fenceArr) {
		int dirtWidth = 32;
		int dirtHeight = 32;

		for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                int x = startX + i * dirtWidth;
                int y = startY + j * dirtHeight;
                DirtArr.add(new Dirt(x, y, 1));
            }
        }	

		fenceArr.add(new Fence(fenceX, fenceY, 1));
	}
	
}
