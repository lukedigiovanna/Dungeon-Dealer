package mapping.gridComponents;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import java.awt.*;

import display.*;

public class CobblestoneWall extends Wall {
	public CobblestoneWall() {
		super();
	}
	
	public BufferedImage getRawImage() {
		BufferedImage img = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		String fileName = "cobblestonetexture";
		ImageIcon img1 = new ImageIcon("lib/assets/images/"+fileName+".png");
		g.drawImage(img1.getImage(), 0, 0, img.getWidth(), img.getHeight(), null);
		//img = ImageProcessor.scaleToColor(img);
		return img;
	}
}
