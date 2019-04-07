package entities.players;

import items.*;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.*;

//the inventory is composed of all the items that is on the player including equipped armor and extra space for spare items
public class Inventory {
	public static final int WIDTH = 9;
	//has 9 slots available to the player at any time -- HOTBAR
	private Item[] items;
	private int selectedIndex = 0;
	
	public Inventory(int size) {
		items = new Item[size];
	}
	
	//methods
	//adds an item in the next available spot -- where the first occurance of a null is.
	//returns false if the inventory is full
	public boolean addItem(Item item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				items[i] = item;
				return true;
			}
		}
		return false;
	}
	
	public void clearBrokenItems() {
		for (int i = 0; i < items.length; i++)
			if (items[i] != null && items[i].getDurability() <= 0)
				items[i] = null;
	}
	
	public Item getSelectedItem() {
		return items[selectedIndex];
	}
	
	public int getSize() {
		return items.length;
	}
	
	public void setSelectedIndex(int index) {
		this.selectedIndex = index;
		if (this.selectedIndex < 0)
			this.selectedIndex = WIDTH-1;
		this.selectedIndex%=WIDTH;
	}
	
	public int getSelectedIndex() {
		return this.selectedIndex;
	}
	
	public Image getImage() {
		int scale = 24;
		int height = (items.length/WIDTH)*scale;
		if (items.length%WIDTH!=0)
			height+=scale;
		int width = items.length*scale;
		if (items.length > WIDTH)
			width = WIDTH*scale;
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		//g.setColor(new Color(150,150,150,150));
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < items.length; i++) {
			g.setColor(new Color(150,150,150,150));
			if (i == selectedIndex)
				g.setColor(Color.CYAN);
			g.fillRect(i%WIDTH*scale+2, i/WIDTH*scale+2, scale-2, scale-2);
			if (items[i] != null) {
				Image itemImg = items[i].getImage();
				if (itemImg != null)
					g.drawImage(itemImg, i%WIDTH*scale+2, i/WIDTH*scale+2, scale-2, scale-2, null);
			}
		}
		return img;
	}
}

