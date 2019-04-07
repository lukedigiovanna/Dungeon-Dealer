package mapping.gridComponents;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Void extends GridComponent {
	public Void() {
		super();
	}
	
	public BufferedImage getRawImage() {
		BufferedImage img = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(new Color(0,0,0,0));
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		//g.setColor(Color.RED);
		//g.drawString("VOID", img.getWidth()/2-g.getFontMetrics().stringWidth("VOID")/2, img.getHeight()/2+g.getFontMetrics().getHeight()/3);
		return img;
	}
}
