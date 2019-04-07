package mapping.gridComponents;

import java.awt.image.BufferedImage;
import java.awt.*;

//walls are components that the entities can't go over.l
public class Wall extends GridComponent {
	public Wall() {
		super();
	}
	
	public BufferedImage getRawImage() {
		BufferedImage bi = new BufferedImage(32,32, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		g.setColor(Color.WHITE);
		g.drawString("WALL", bi.getWidth()/2-g.getFontMetrics().stringWidth("WALL")/2, bi.getHeight()/3);
		return bi;
	}
}
