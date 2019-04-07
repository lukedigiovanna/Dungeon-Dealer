package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Information {
	public static final String VERSION = "Indev 0.6";
	public static final String CREATOR = "Luke DiGiovanna";
	public static final String BUSINESS = "LAD Games";
	public static final String GAME_NAME = "Dungeon Dealers";
	public static final String RELEASE_INFO = "Development January 2019";
	public static final String FRAME_NAME = GAME_NAME+" "+VERSION+" | "+RELEASE_INFO;
	
	public static BufferedImage getImage() {
		String[] notes = {
				GAME_NAME+" "+VERSION,
				RELEASE_INFO,
				BUSINESS
		};
		BufferedImage img = new BufferedImage(500,notes.length*15+15,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(new Color(255,255,255,0));
		g.fillRect(0,0,img.getWidth(),img.getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.PLAIN,14));
		for (int i = 0; i < notes.length; i++) {
			int width = g.getFontMetrics().stringWidth(notes[i]);
			//right align the text
			int x = img.getWidth()-width;
			int y = i*15+15;
			g.drawString(notes[i], x, y);
		}
		return img;
	}
}
