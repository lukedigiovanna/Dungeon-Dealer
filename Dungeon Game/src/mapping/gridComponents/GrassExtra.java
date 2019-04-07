package mapping.gridComponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class GrassExtra extends GridComponentExtra {
	
	@Override
	public void setImage() {
		BufferedImage img = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		ImageIcon ico = new ImageIcon("lib/assets/images/grass.png");
		g.drawImage(ico.getImage(),0,0,img.getWidth(),img.getHeight(),null);
		setImage(img);
	}
}
