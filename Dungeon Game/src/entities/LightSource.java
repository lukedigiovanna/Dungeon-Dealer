package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import display.ImageProcessor;

public class LightSource extends Entity {
	public Color color;
	public double strength;
	
	public LightSource(double x, double y, double strength) {
		super(x,y);
		this.strength = strength;
		setWidth(0);
		setHeight(0);
	}
	
	public LightSource(double strength) {
		this(0,0,strength); 
		//if it is initialized with this constructor then it should have a reference in another entity.. 
		//in that case then the other entity will constantly set this entities position
	}
	
	public static double getLightValue(double x, double y) {
		double value = 0.0;
		for (int i = 0; i < Entity.entities.size(); i++) {
			if (Entity.entities.size() > 0 && i < Entity.entities.size() && Entity.entities.get(i) instanceof LightSource) {
				LightSource ls = (LightSource)Entity.entities.get(i);
				if (ls == null)
					continue;
				double distance = Math.sqrt((ls.getX()-x)*(ls.getX()-x)+(ls.getY()-y)*(ls.getY()-y));
				double toAdd = 1/distance*ls.getStrength();
				if (toAdd > 0.1)
					value+=toAdd;
			}
		}
		value = misc.Number.clip(value, 0, 1);
		return value;
	}
	
	public double getStrength() {
		return strength;
	}

	@Override
	//returns nothing
	public Image getRawImage() {
		BufferedImage img = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0, 0, Color.yellow.getRGB());
		return img;
	}

	@Override
	public void update() {
		//nothing to do here...
	}
}
