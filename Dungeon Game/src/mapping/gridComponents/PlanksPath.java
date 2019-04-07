package mapping.gridComponents;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import display.ImageProcessor;

public class PlanksPath extends Path {
	public PlanksPath() {
		super();
		if (Math.random() < 0.1) 
			addExtra(new GrassExtra());
	}
	
	public BufferedImage getRawImage() {
		BufferedImage img = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		ImageIcon ico = new ImageIcon("lib/assets/images/plankstexture.png");
		g.drawImage(ico.getImage(),0,0,img.getWidth(),img.getHeight(),null);
		paintExtras(g);
		return img;
	}
}
