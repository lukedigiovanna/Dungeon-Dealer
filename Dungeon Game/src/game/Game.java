package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import console.Console;
import mapping.*;
import mapping.gridComponents.*;
import entities.players.*;
import display.*;
import entities.Entity;
import main.Driver;

public class Game {
	private Dungeon dungeon;
	private Display display;
	
	public Game(Display display) {
		this.display = display;
		init();
	}
	
	public void init() {
		Entity.entities.clear();
		dungeon = new Dungeon();
		Grid grid = dungeon.getLevel().getGrid();
		
		Entity.entities.add(new Player(grid.getWidth()/2,grid.getHeight()/2+2,0.7,0.7));
		
		dungeon.getLevel().getCamera().setEntityToFocus(Entity.entities.get(0));
		
		for (int i = 0; i < 250; i++)
			Entity.entities.add(new enemies.Spider(Math.random()*grid.getWidth(),Math.random()*grid.getHeight(),0.4,0.4));
		for (Entity e : Entity.entities)
			e.setCurrentLevel(dungeon.getLevel());
		//pause on initiation.. later is told to resume when we want to activate the game
		this.pauseThreads();
	}
	
	public void initListeners() {
		for (Entity e : Entity.entities) 
			if (e instanceof Player)
				((Player) e).addKeyListener(Driver.panel);
	}
	
	public void pauseThreads() {
		ArrayList<Entity> entities = Entity.entities;
		for (int i = 0; i < entities.size(); i++) 
			if (entities.get(i)!=null)
				entities.get(i).pauseUpdateThread();
	}
	
	public void resumeThreads() {
		ArrayList<Entity> entities = Entity.entities;
		for (int i = 0; i < entities.size(); i++) 
			if (entities.get(i) != null)
				entities.get(i).resumeUpdateThread();
	}
	
	public void draw() {
		if (display == null)
			return;
		//draw the background
		double guiWidth = 0.3;
		double guiHeight = 0.25;
		
		int width = display.getWidth();
		int height = display.getHeight();
		Graphics g = display.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		//draw the camera
		if (dungeon == null) {
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,48));
			String s = "ERROR: NO DUNGEON FOUND";
			g.drawString(s, display.getWidth()/2-g.getFontMetrics().stringWidth(s)/2, display.getHeight()/2);
			return;
		}
		BufferedImage cameraView = dungeon.getCurrentCameraView();
		g.setColor(Color.GRAY);
		int borderWidth = 10;
		g.fillRect(0,0,(int)(display.getWidth()*(1-guiWidth)), (int)(display.getHeight()*(1-guiHeight)));
		g.drawImage(cameraView, borderWidth, borderWidth, (int)(display.getWidth()*(1-guiWidth))-borderWidth*2, (int)(display.getHeight()*(1-guiHeight))-borderWidth*2, null);
		
		//draw gui stuff too. 
		double scale = (double)display.getWidth()*display.getHeight();
		double x = 0.0000291666666; //a scalar quantity derived from one set value that is used to work for all values
		g.setFont(new Font("Cordova", Font.BOLD, (int)(scale*x)));
		g.setColor(Color.RED);
		double health = Entity.getPlayer().getHealth();
		double maxHealth = Entity.getPlayer().getMaxHealth();
		int xAlign = (int)(width*(1-guiWidth))+10;
		//draw the health
		String healthString = "Health: "+health+"/"+maxHealth+" ("+(int)(Entity.getPlayer().getPercentHealth()*10000)/100.0+"%)";
		g.drawString(healthString, xAlign, 50);
		int stringWidth = g.getFontMetrics().stringWidth("Health");
		g.setColor(new Color(50,50,50));
		g.fillRect(xAlign+stringWidth+5-2, 60-2, (int)((guiWidth*width)-stringWidth-10-25)+4, 10+4);
		g.setColor(Color.BLACK);
		g.fillRect(xAlign+stringWidth+5, 60, (int)((guiWidth*width)-stringWidth-10-25), 10);
		g.setColor(new Color(255-(int)(Entity.getPlayer().getPercentHealth()*255),(int)(Entity.getPlayer().getPercentHealth()*255),0));
		g.fillRect(xAlign+stringWidth+5, 60, (int)(((guiWidth*width)-stringWidth-10-25)*(Entity.getPlayer().getPercentHealth())), 10);
		
		//draw the mana
		g.setColor(Color.CYAN);
		g.drawString(Entity.getPlayer().getManaString(),xAlign, 90);
		g.setColor(new Color(50,50,50));
		g.fillRect(xAlign+stringWidth+5-2, 100-2, (int)((guiWidth*width)-stringWidth-10-25)+4, 10+4);
		g.setColor(Color.BLACK);
		g.fillRect(xAlign+stringWidth+5, 100, (int)((guiWidth*width)-stringWidth-10-25), 10);
		g.setColor(new Color(255-(int)(255*Entity.getPlayer().getManaPercent()),0,(int)(255*Entity.getPlayer().getManaPercent())));
		g.fillRect(xAlign+stringWidth+5, 100, (int)(((guiWidth*width)-stringWidth-10-25)*(Entity.getPlayer().getManaPercent())), 10);
		String[] notes = {"Gold 💰: "+Entity.getPlayer().getGold(), 
						"Strength 🗡: "+Entity.getPlayer().getStrength(), 
						"Speed ☄: "+Entity.getPlayer().getSpeed(),
						"Luck ☘: "+Entity.getPlayer().getLuckString(),
						"Resistance 🛡: "+Entity.getPlayer().getResistance(),
						"Level: "+dungeon.getLevelNumber(),
						"", //line space
						"Position: "+(int)(Entity.getPlayer().getX()*100)/100.0+", "+(int)(Entity.getPlayer().getY()*100)/100.0,
						"Memory being used: "+main.Runtime.getUsedMemory()/1000000+" MB",
						"FPS: "+main.Driver.panel.getFPS(),
						"Entities: "+Entity.entities.size()
		};
		Color[] colors = {Color.YELLOW,Color.RED.darker(),Color.BLUE,Color.GREEN,Color.LIGHT_GRAY,Color.WHITE,Color.GRAY};
		for (int i = 0; i < notes.length; i++) {
			if (i < colors.length)
				g.setColor(colors[i]);
			g.drawString(notes[i], xAlign, i*20+130);
		}
		
		Image invImg = Entity.getPlayer().getAttribs().inventory.getImage();
		int invW = 1, invH = 1;
		int rightWidth = (int)(guiWidth*width);
		double scaleWidth = .6;
		invW = (int)(rightWidth*scaleWidth);
		invH = (invW/Inventory.WIDTH)*(1+Entity.getPlayer().getAttribs().inventory.getSize()/Inventory.WIDTH);
		g.setColor(Color.WHITE);
		g.drawString("Inventory: ", xAlign, notes.length*20+140);
		invW = (int)(invW*1.3);
		invH = (int)(invH*1.3);
		g.drawImage(invImg, xAlign+30, notes.length*20+150, invW, invH, null);
		
		if (Entity.getPlayer().getAttribs().inventory.getSelectedItem() != null)
			g.drawImage(Entity.getPlayer().getAttribs().inventory.getSelectedItem().getInfoImage(),xAlign,notes.length*20+170+invH,rightWidth,96,null);
	}
	
	public Dungeon getDungeon() {
		return dungeon;
	}
}
