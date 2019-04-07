package entities.players;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import display.ImageProcessor;
import entities.*;
import entities.damageBox.*;
import items.weapons.*;
import items.consumables.*;

public class Player extends Entity {
	//instance variables
	private PlayerAttribs attribs = new PlayerAttribs();
	private KeyListener key = new Key();
	private boolean moveRight = false, moveLeft = false, moveUp = false, moveDown = false; //if it is true then it will move that dir
	public static enum Move {RIGHT,LEFT,DOWN,UP,MOVE,STOP};
	
	public Player(double x, double y, double width, double height) {
		super(x, y, width, height);
		health=maxHealth=20; //simple default value
		attribs.mana=attribs.maxMana=30;
		attribs.luck=1;
		attribs.strength=5;
		attribs.inherentResistance=0;
		attribs.inventory = new Inventory(27);
		attribs.inventory.addItem(new WoodenBroadSword(this));
		attribs.inventory.addItem(new WoodenBroadSword(this));
		attribs.inventory.addItem(new WoodenBroadSword(this));
		attribs.inventory.addItem(new Apple());
		attribs.inventory.addItem(new Apple());
		attribs.inventory.addItem(new Apple());
		attribs.inventory.addItem(new Apple());
		addLightSource(new LightSource(2));
		setSpeed(0.15);
	}
	
	public Image getRawImage() {
		int width = (int)(32*getWidth());
		int height = (int)(32*getHeight());
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		//makes the background transparent
		g.setColor(new Color(0,0,0,0));
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		//draw the player
		ImageIcon ico = new ImageIcon("lib/assets/images/tempplayer.png");
		g.drawImage(ico.getImage(),0,0,width,height,null);
		switch (orientation) {
		case WEST:
			img = ImageProcessor.flipY(img);
			break;
		case EAST:
			break;
			
		}
		return img;
	}
	
	public void update() {
		move();	
		if (attribs.inventory != null)
			attribs.inventory.clearBrokenItems();
	}
	
	public void addKeyListener(JPanel panel) {
		if (this.key != null && panel != null)
			panel.addKeyListener(this.key);
	}
	
	private class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.isShiftDown()) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					attribs.inventory.setSelectedIndex(attribs.inventory.getSelectedIndex()+1);
					return;
				case KeyEvent.VK_LEFT:
					attribs.inventory.setSelectedIndex(attribs.inventory.getSelectedIndex()-1);
					return;
				}
			}
			switch (e.getKeyCode()) {
			case Controls.MOVE_UP:
				moveDirection(Move.UP,Move.MOVE);
				break;
			case Controls.MOVE_DOWN:
				moveDirection(Move.DOWN,Move.MOVE);
				break;
			case Controls.MOVE_RIGHT:
				moveDirection(Move.RIGHT,Move.MOVE);
				break;
			case Controls.MOVE_LEFT:
				moveDirection(Move.LEFT,Move.MOVE);
				break;
				
			case KeyEvent.VK_UP:
				orientation = Orientation.NORTH;
				if (attribs.inventory.getSelectedItem() instanceof items.weapons.Weapon)
					attribs.inventory.getSelectedItem().use();
				break;
			case KeyEvent.VK_RIGHT:
				orientation = Orientation.EAST;
				if (attribs.inventory.getSelectedItem() instanceof items.weapons.Weapon)
					attribs.inventory.getSelectedItem().use();
				break;
			case KeyEvent.VK_DOWN:
				orientation = Orientation.SOUTH;
				if (attribs.inventory.getSelectedItem() instanceof items.weapons.Weapon)
					attribs.inventory.getSelectedItem().use();
				break;
			case KeyEvent.VK_LEFT:
				orientation = Orientation.WEST;
				if (attribs.inventory.getSelectedItem() instanceof items.weapons.Weapon)
					attribs.inventory.getSelectedItem().use();
				break;
				
			case KeyEvent.VK_E:
				if (attribs.inventory.getSelectedItem() != null)
					attribs.inventory.getSelectedItem().use();
				break;
				
			case KeyEvent.VK_1:
				attribs.inventory.setSelectedIndex(0);
				break;
			case KeyEvent.VK_2:
				attribs.inventory.setSelectedIndex(1);
				break;
			case KeyEvent.VK_3:
				attribs.inventory.setSelectedIndex(2);
				break;
			case KeyEvent.VK_4:
				attribs.inventory.setSelectedIndex(3);
				break;
			case KeyEvent.VK_5:
				attribs.inventory.setSelectedIndex(4);
				break;
			case KeyEvent.VK_6:
				attribs.inventory.setSelectedIndex(5);
				break;
			case KeyEvent.VK_7:
				attribs.inventory.setSelectedIndex(6);
				break;
			case KeyEvent.VK_8:
				attribs.inventory.setSelectedIndex(7);
				break;
			case KeyEvent.VK_9:
				attribs.inventory.setSelectedIndex(8);
				break;
			//random bs to test shit	
			case KeyEvent.VK_G:
				Player.this.attribs.inventory.addItem(new Apple());
			}
		}
		
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case Controls.MOVE_UP:
				moveDirection(Move.UP,Move.STOP);
				break;
			case Controls.MOVE_DOWN:
				moveDirection(Move.DOWN,Move.STOP);
				break;
			case Controls.MOVE_RIGHT:
				moveDirection(Move.RIGHT,Move.STOP);
				break;
			case Controls.MOVE_LEFT:
				moveDirection(Move.LEFT,Move.STOP);
				break;
			}
		}
	}
	
	Color[] loop = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.MAGENTA};
	int ind = 0;
	public void move() {
		//handles the moving
		//reset the xv and yv first
		double xv = 0;
		double yv = 0;
		//this method allows the entity to hold both up and down (or left and right) and not move. gives for smooth direction change and movement
		if (moveRight) {
			xv+=getSpeed();
			orientation = Orientation.EAST;
		}
		if (moveLeft) {
			xv-=getSpeed();
			orientation = Orientation.WEST;
		}
		if (moveUp)
			yv-=getSpeed();
		if (moveDown)
			yv+=getSpeed();
		if (!doubleEquals(xv,0) || !doubleEquals(yv,0)) {
			((Player)this).useMana(0.1);
			ImageIcon img = new ImageIcon("lib/assets/images/fireball.png");
			BufferedImage image = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();
			g.drawImage(img.getImage(), 0, 0, image.getWidth(), image.getHeight(), null);
			Color n = loop[ind%loop.length];
			ind++;
			image = ImageProcessor.scaleToColor(image, n);
			//PopMessage.addPopMessage(new PopMessage(image,getX(),getY()));
		}
		else
			((Player)this).useMana(-0.1);
		move(xv,yv);
	}
	
	public void moveDirection(Move dir, Move what) {
		if (what == Move.MOVE) {
			switch (dir) {
			case RIGHT:
				moveRight = true;
				break;
			case LEFT:
				moveLeft = true;
				break;
			case UP:
				moveUp = true;
				break;
			case DOWN:
				moveDown = true;
				break;
			default:
				break;
			}
		} else if (what == Move.STOP) {
			switch (dir) {
			case RIGHT:
				moveRight = false;
				break;
			case LEFT:
				moveLeft = false;
				break;
			case UP:
				moveUp = false;
				break;
			case DOWN:
				moveDown = false;
				break;
			default:
				break;
			}
		}
	}

	public void useMana(double amount) {
		setMana(getMana()-amount);
	}
	
	public double getPercentHealth() {
		if (maxHealth == 0)
			return 0;
		double percent = (health/maxHealth);
		percent = round(percent,0.001);
		if (percent < 0)
			percent = 0;
		return percent;
	}
	
	public void addGold(int amount) {
		this.attribs.gold+=amount;
	}
	
	public boolean removeGold(int amount) {
		if (this.attribs.gold-amount >= 0) {
			this.attribs.gold-=amount;
			return true;
		}
		return false;
	}
	
	public int getGold() {
		return this.attribs.gold;
	}
	
	public double getStrength() {
		return attribs.strength;
	}
	
	public double getLuck() {
		return attribs.luck;
	}
	
	public String getLuckString() {
		return round((attribs.luck)*100,0.01)+"%";
	}
	
	public double getResistance() {
		return round(attribs.armorPoints+attribs.inherentResistance,0.01);
	}
	
	public double getMana() {
		return round(attribs.mana,0.01);
	}
	
	public void setMana(double m) {
		this.attribs.mana = m;
		if (this.attribs.mana < 0)
			this.attribs.mana = 0;
		if (this.attribs.mana > this.attribs.maxMana)
			this.attribs.mana = this.attribs.maxMana;
	}
	
	public double getMaxMana() {
		return round(attribs.maxMana,0.1);
	}
	
	public double getManaPercent() {
		return round(attribs.mana/attribs.maxMana,0.001);
	}
	
	public String getManaString() {
		return "Mana: "+getMana()+"/"+getMaxMana()+" ("+round(getManaPercent()*100,0.1)+"%)";
	}
	
	public PlayerAttribs getAttribs() {
		return attribs;
	}
}
