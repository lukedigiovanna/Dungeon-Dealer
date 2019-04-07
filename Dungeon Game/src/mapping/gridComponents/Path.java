package mapping.gridComponents;

import java.awt.image.BufferedImage;

import display.ImageProcessor;

import java.awt.*;

public class Path extends GridComponent {
	private Color scaleColor = new Color(255,255,255,0);
	
	public Path() {
		super();
	}
	
	public Path(Color scaleColor) {
		super();
		this.scaleColor = scaleColor;
	}
	
	public BufferedImage getRawImage() {
		BufferedImage img = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.ORANGE.darker());
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		for (int x = 0; x < img.getWidth(); x++) 
			for (int y = 0; y < img.getHeight(); y++) {
				int color = (int)(Math.random()*25)+100;
				g.setColor(new Color((int)(color*1.5),color,0));
				g.fillRect(x, y, 1, 1);
			}
		img = ImageProcessor.scaleToColor(img, scaleColor);
		return img;
	}
}
