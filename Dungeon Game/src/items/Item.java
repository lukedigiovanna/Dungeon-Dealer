package items;

import java.awt.*;
import java.awt.image.BufferedImage;

import entities.*;

//superclass for all items
public abstract class Item {
	protected String displayName = "Generic Item", description = "";
	protected int durability, maxDurability;
	protected Entity owner;
	
	public Item() {
		//by default the owner is the player
		this(Entity.getPlayer());
	}
	
	public Item(Entity owner) {
		this.owner = owner;
		this.durability=this.maxDurability=1;
	}
	
	public Image getImage() {
		return new misc.UnknownImage();
	}
	
	public abstract void use();
	
	public Image getInfoImage() {
		int height = 96;
		int width = 300;
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		//for every item... the image and the name
		g.setFont(new Font("Serif",Font.BOLD,18));
		int nameWidth = g.getFontMetrics().stringWidth(displayName);
		g.drawString(displayName + "-", 10, 14);
		g.drawImage(getImage(), 10+nameWidth+g.getFontMetrics().stringWidth("-"), 0, 18, 18, null);
		g.drawString(description, 10, 46);
		g.drawString("Durability: "+durability+"/"+maxDurability, 10, 69);
		return img;
	}
	
	public int getDurability() {
		return durability;
	}
}
