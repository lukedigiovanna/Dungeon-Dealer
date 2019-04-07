package items.consumables;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import entities.Entity;

public class Apple extends Consumable {
	public Apple() {
		this(Entity.getPlayer());
	}
	
	public Apple(Entity owner) {
		this.displayName = "Apple";
		this.description = "Heals 3 health";
	}
	
	public void use() {
		Entity.getPlayer().heal(3);
		this.durability--;
	}
	
	public Image getImage() {
		BufferedImage img = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		ImageIcon ico = new ImageIcon("lib/assets/images/apple.png");
		g.drawImage(ico.getImage(),0,0,32,32,null);
		return img;
	}
}
