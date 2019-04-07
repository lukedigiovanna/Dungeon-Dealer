package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import console.Console;
import mapping.*;                                                                                                                                                      
 
//used as a superclass to anything that is rendered on the grid. GridComponents are NOT entities.
public abstract class Entity {
	protected int age = 0;
	protected boolean hasCollision = true, canMove = true;
	private LightSource lightSource = null;
	protected double lightValue = 0.2;
	
	public static enum Type {ENTITY, PLAYER, ENEMY, DAMAGE_BOX, ITEM, POP_MESSAGE};
	public Type type = Type.ENTITY;
	public static enum Orientation {WEST,EAST,NORTH,SOUTH};
	public Orientation orientation = Orientation.EAST;
	//all entites have an x,y position on the grid
	protected double x, y;
	private double prevX, prevY; //used for testing collision after movement
	protected double width = 1.0, height = 1.0;
	protected double xv, yv; //x and y velocities
	protected double speed = 0.2;
	protected double health = 1, maxHealth = 1;
	protected boolean invulnerable = false;
	private Level currentLevel;
	private Thread thread;
	
	public Entity(double x, double y) {
		this(x,y,1,1); //uses the default width/height
	}
	
	public Entity(double x, double y, double width, double height) {
		//set the values
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		//start an update thread that is called every 50ms. this mainly handles movement (at the moment)
		thread = new Thread(new UpdateThread());
		thread.start();
	}

	private class UpdateThread implements Runnable {
		public void run() {
			//so long as the thread isn't null
			while (thread == Thread.currentThread()) { //until the entity doesn't exist anymore...
				try {
					Thread.sleep(50); //wait 50ms
				} catch (InterruptedException ex) {
					//dont do anything
				}
				update_ForAll();
				update(); //call the update method
			}
		}
	}
	
	private void update_ForAll() {
		//updates for all entities
		
		//increase the age where each increment of 50 is one 'tick'
		age+=50;
		
		//if the entity has a light source then move that source to wherever the entity is...
		if (this.lightSource != null) {
			this.lightSource.setX(this.x);
			this.lightSource.setY(this.y);
		}
		
		//update the entities light value
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof LightSource) 
				this.lightValue = LightSource.getLightValue(entities.get(i).getX(),entities.get(i).getY());
		}
	}
	
	//some stuff to handle the collection of entities
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public static entities.players.Player getPlayer() {
		if (entities.size() == 0)
			return null;
		for (Entity e : entities) 
			if (e instanceof entities.players.Player)
				return (entities.players.Player)e;
		return null;
	}
	
	public static void init() {
		//creates a refresher thread
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(250);
					} catch (Exception e) {}
					refresh();
				}
			}
		});
		t.start();
	}
	
	//called every 250 ms
	public static void refresh() {
		for (int i = 0; i < entities.size(); i++)
			if (i < entities.size() && entities.get(i) != null)
				entities.get(i).resumeUpdateThread();
		removeNullReferences();
	}
	
	private static void removeNullReferences() {
		for (int i = entities.size()-1; i >= 0; i--) 
			if (entities.get(i) == null)
				entities.remove(i);
	}
	
	public void pauseUpdateThread() {
		if (thread != null) {
			Thread wait = thread;
			thread = null;
			wait.interrupt();
		}
	}
	
	public void resumeUpdateThread() {
		//start up the thread again.
		thread = new Thread(new UpdateThread());
		thread.start();
	}
	
	public Image getImage() {
		return display.ImageProcessor.darken((BufferedImage)getRawImage(), 1-this.lightValue);
	}
	
	//this is used to draw the entity however it should be drawn
	public abstract Image getRawImage();
	//called every 50ms by a thread. used for doing general updates for the entity (NOT DRAWING)
	public abstract void update();
	
	//sets the x and y components of the velocity given a direction (angle in degrees) and a magnitude (in world units)
	public void setVelocity(double degree, double magnitude) {
		//first convert the degree into radians for the cos and sin functions to use
		degree = degree*Math.PI/180;
		
		//use trig here to get the individual components

		//uses system where 0-90 degrees is the I quadrant, 90-180 is II, 180-270 is III, and 270-360 is IV
		//to do this we need to translate our degree given how our world coordinate system works where the I quadrant is where the IV quadrant is 
		//just negate the sine function to do this
		setXV(Math.cos(degree)*magnitude);
		setYV(-Math.sin(degree)*magnitude);
	}
	
	//if it intersects an entity
	public boolean intersects(Entity e) {
		if (e == null)
			return false;
		return intersects(e.getX(),e.getY(),e.getWidth(),e.getHeight());
	} 
	
	//returns true if it intersects anywhere between the previous coordinates and the new coordinates
	public boolean intersects(double x, double y, double width, double height) {
		return (intersects(x,y,width,height,this.x,this.y,this.width,this.height));
			
//		double xInc = (this.x-prevX)/20.0; //tests 20 places between the previous and max
//		double yInc = (this.y-prevY)/20.0;
//		double thisX = prevX;
//		double thisY = prevY;
//		//uses do while loops to make sure it tests at least once
//		do { //test x case
//			do { //test y case
//				if (intersects(x,y,width,height,thisX,thisY,this.width,this.height))
//					return true;
//				thisY+=yInc;
//				if (thisY < 1000 || thisY > 1000) //if it somehow gets stuck in a loop
//					break;
//			} while(!doubleEquals(thisY,this.y));
//			thisX+=xInc;
//		} while (!doubleEquals(thisX,this.x));
//		return false;
	}
	
	//returns true if the two boxes intersect
	private boolean intersects(double x1, double y1, double width1, double height1, double x2, double y2, double width2, double height2) {
		if (x1+width1 > x2 && x1 < x2+width2)
			if (y1 < y2+height2 && y1+height1 > y2)
				return true;
		return false;
	}
	
	//returns the position of the wall that the entity is intersecting, if there is no wall it returns null
	private Position intersectsWall(Grid grid, double x1, double y1) {
		//now only test where a wall could even be
		//uses this so that large maps and large amounts of entities does not lead to lag or overworking the computer
		int xStart = (int)x1;
		if (xStart < 0)
			xStart = 0;
		int xStop = xStart+2;
		if (xStop > grid.getGrid().length)
			xStop = grid.getGrid().length;
		int yStart = (int)y1;
		if (yStart < 0)
			yStart = 0;                                                                                                                                                                                                                                                                          
		int yStop = yStart+2;
		if (yStop > grid.getGrid()[0].length)
			yStop = grid.getGrid()[0].length;
		for (int x = xStart; x < xStop; x++)
			for (int y = yStart; y < yStop; y++)
				if (grid.getGrid()[x][y] instanceof mapping.gridComponents.Wall) //if its a wall grid component
					if (intersects(x1,y1,this.width,this.height,x,y,1,1))
						return new Position(x,y);
		return null;
	}
	
	protected Position intersectsWall() {
		return intersectsWall(currentLevel.getGrid(),this.x,this.y);
	}
	
	private class Position {
		public double x;
		public double y;
		public Position(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
	
	protected boolean doubleEquals(double n1, double n2) {
		if (Math.abs(n1-n2) < 0.01)
			return true;
		return false;
	}
	
	public void move(double dx, double dy) {
		if (!canMove)
			return;
		//in the setX and setY methods, it ensures that the x and y will not be within a wall piece
		//if there is no level then just dont even move cuz it doesnt matter and will cause errors in the code
		if (currentLevel == null || currentLevel.getGrid() == null)
			return;
		if (!hasCollision) {
			setX(this.x+dx);
			setY(this.y+dy);
			return;
		}
		double realDX = dx;
		double realDY = dy;
		double xInc = 0.05; if (dx < 0) xInc = -0.05;
		double yInc = 0.05; if (dy < 0) yInc = -0.05;
		while (intersectsWall(currentLevel.getGrid(),this.x+realDX,this.y) != null)
			realDX-=xInc;
		while (intersectsWall(currentLevel.getGrid(),this.x,this.y+realDY) != null)
			realDY-=yInc;
		setX(this.x+realDX);
		setY(this.y+realDY);
		roundXY(); //ensures further errors are prevented by round-off errors

		//this eliminates any potential error of the entity getting stuck in the wall.
		Position intersectingWall = intersectsWall(currentLevel.getGrid(),this.x,this.y);
		if (intersectingWall != null) {
			//first set it to where the wall is
			setX(intersectingWall.x);
			setY(intersectingWall.y);
			//based on the direction that the player was trying to move...
			if (dx > 0)
				setX(getX()-this.width);
			if (dx < 0)
				setX(getX()+1);
			if (dy > 0)
				setY(getY()-this.height);
			if (dy < 0)
				setY(getY()+1);
		}
	}
	
	public void destroy() {
		if (this.lightSource != null)
			this.lightSource.destroy();
		int index = entities.indexOf(this);
		if (index < 0 || this == null)
			return;
		Thread temp = this.thread;
		thread = null;
		if (temp != null)
			temp.interrupt();
		//entities.remove(this);

		entities.set(index, null);
		ArrayList<Entity> newArr = new ArrayList<Entity>();
//		for (int i = 0; i < entities.size(); i++) 
//			if (i < entities.size() && entities.get(i) != null)
//				newArr.add(entities.get(i));
//		entities = newArr;
	}
	
	//number stuff
	private void roundXY() {
		int rx = (int)(this.x*1000);
		this.x = rx/1000.0;
		int ry = (int)(this.y*1000);
		this.y = ry/1000.0;
	}
	
	protected double round(double num, double place) { //place should be presented as 0.01, 0.1 etc..
		double multiplier = 1/place;
		double rounded = (int)(num*multiplier)/multiplier;
		return rounded;
	}
	
	//getters and setters
	public void setCurrentLevel(Level l) {
		currentLevel = l;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		prevX = this.x;
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		prevY = this.y;
		this.y = y;
	}

	public double getXV() {
		return xv;
	}

	public void setXV(double xv) {
		this.xv = xv;
	}

	public double getYV() {
		return yv;
	}

	public void setYV(double yv) {
		this.yv = yv;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getPrevX() {
		return prevX;
	}

	public void setPrevX(double prevX) {
		this.prevX = prevX;
	}

	public double getPrevY() {
		return prevY;
	}

	public void setPrevY(double prevY) {
		this.prevY = prevY;
	}	
	
	public Level getCurrentLevel() {
		return currentLevel;
	}
	
	public double getHealth() {
		return round(health,0.01);
	}

	public void setHealth(double health) {
		this.health = health;
		if (this.health <= 0) 
			this.health = 0;
		if (this.health > this.maxHealth)
			this.health = this.maxHealth;
	}
	
	public void hurt(double amount) {
		Color c = Color.RED;
		if (this instanceof enemies.Enemy)
			c = Color.YELLOW;
		PopMessage.addPopMessage(new PopMessage("-"+amount,getX()+getWidth()/2,getY(),c));
		setHealth(getHealth()-amount);
	}
	
	public void heal(double amount) {
		PopMessage.addPopMessage(new PopMessage("+"+amount,getX()+getWidth()/2,getY(),Color.GREEN));
		setHealth(getHealth()+amount);
	}

	public double getMaxHealth() {
		return round(maxHealth,0.01);
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public double getCenterX() {
		return x+width/2.0;
	}
	
	public double getCenterY() {
		return y+height/2.0;
	}
	
	public void setHasCollision(boolean f) {
		this.hasCollision = f;
	}
	
	public LightSource getLightSource() {
		return lightSource;
	}
	
	public void addLightSource(LightSource ls) {
		this.lightSource = ls;
		entities.add(ls);
	}
}
