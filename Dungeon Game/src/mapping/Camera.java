package mapping;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

import display.ImageProcessor;
import main.Driver;
import mapping.gridComponents.GridComponent;
import entities.*;

//this class is used to know what part of the grid to display
public class Camera {
	//these values give the x,y,width,height of the grid
	
	private double x, y; 
	private int width, height;
	private int resScale = 32;
	private Level level;
	private Entity entityToFocus;
	
	public Camera(double x, double y, int width, int height, Level level) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.level = level;
		for (Entity e : Entity.entities)
			if (e instanceof entities.players.Player)
				entityToFocus = e;
	}
	
	public BufferedImage getView() {
		BufferedImage img = new BufferedImage(Driver.WIDTH,Driver.HEIGHT,BufferedImage.TYPE_INT_ARGB);
		//first adjust the camera
		if (entityToFocus != null)
			adjustToEntity(entityToFocus);
		Graphics g = img.getGraphics();
		g.setColor(new Color(0,25,50));
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		int compWidth = img.getWidth()/this.width;
		int compHeight = img.getHeight()/this.height;
		double xOffset = x-(int)x; //if its 1.5 it returns 0.5, 2.33 -> .33
		double yOffset = y-(int)y;
		GridComponent[][] grid = level.getGrid().getGrid();
		//loops through all the grid components that are touching the camera bounds
		for (int gridX = (int)this.x; gridX < (int)this.x+this.width+1; gridX++) {
			for (int gridY = (int)this.y; gridY < (int)this.y+this.height+1; gridY++) {
				Image gImg = null;
				//many conditions are tested to ensure no errors are presented -- mainly to prevent out of bounds exceptions
				if (gridX < grid.length && gridX > -1 && gridY < grid[gridX].length && gridY > -1 && grid[gridX][gridY] != null && gridX > -1 && gridY > -1 && gridX < level.getGrid().getWidth() && gridY < level.getGrid().getHeight()) { 
					double lv = LightSource.getLightValue(gridX+0.5, gridY+0.5);
					grid[gridX][gridY].setLightValue(lv);
					gImg = grid[gridX][gridY].getImage();
				}
				g.drawImage(gImg, //image 
							(gridX-(int)x)*compWidth-(int)(compWidth*xOffset),(gridY-(int)y)*compHeight-(int)(compHeight*yOffset), //top left x, y coords
							compWidth,compHeight, //width and height
							null);
			}
		}	
		
		//camera needs to draw entities a part of the level being drawn too
		//NOTE: THIS METHOD JUST HANDLES THE DRAWING OF THE ENTITIES!!! other things like logic and updating is done elsewhere
		for (int i = 0; i < Entity.entities.size(); i++) {
			Entity e = Entity.entities.get(i);
			if (e == null)
				continue;
			int gridX = (int)e.getX();
			int gridY = (int)e.getY();
			//if the entity is in the scope of the camera on the grid, then draw it
			if (gridX+1 > this.x && gridX < this.x+this.width && gridY+1 > this.y && gridY < this.y+this.height) {
				int imgX = (gridX-(int)this.x); //first get the x grid on the camera
				imgX*=compWidth; //adjust it to fit on the image to scale
				double entityXOffset = (e.getX()-(int)e.getX()); //how far it is not on the grid...
				imgX+=(entityXOffset*compWidth); //to allow the entity to be inbetween grid components.. this allows for smooth movement 
				imgX-=(xOffset*compWidth);//adjust to camera offset
				int imgY = (gridY-(int)this.y); //same as x
				imgY*=compHeight;
				double entityYOffset = (e.getY()-(int)e.getY());
				imgY+=(entityYOffset*compHeight);
				imgY-=(yOffset*compHeight);
				g.drawImage((BufferedImage)e.getImage(), imgX, imgY,(int)(compWidth*e.getWidth()),(int)(compHeight*e.getHeight()),null);
			}
		}
	
		
		//draw the pop messages		
		for (int i = 0; i < PopMessage.getMessages().size(); i++) {
			PopMessage msg = PopMessage.getMessages().get(i);
			int x = (int)((msg.getX()-this.x)*compWidth);
			int y = (int)((msg.getY()-this.y)*compHeight);
			g.drawImage(msg.getImage(), x-msg.getImage().getWidth()/2, y-msg.getImage().getHeight()/2, msg.getImage().getWidth(), msg.getImage().getHeight(), null);
		}
		
		return img;
	}
		
	public void adjustToEntity(Entity e) {
		double centerX = this.x+(this.width/2);
		double centerY = this.y+(this.height/2);
		double marginX = this.width/8;
		double marginY = this.height/8;
		double speed = e.getSpeed();
		double dist = Math.sqrt((e.getX()-centerX)*(e.getX()-centerX)+(e.getY()-centerY)*(e.getY()-centerY));
		if (dist > 10)
			speed = 10;
		if (e.getX() < centerX-marginX)
			this.x-=speed;
		if (e.getX() > centerX+marginX)
			this.x+=speed;
		if (e.getY() > centerY+marginY)
			this.y+=speed;
		if (e.getY() < centerY-marginY)
			this.y-=speed;
	}
	
	public void zoomIn() {
		this.width*=0.75;
		this.height*=0.75;
		if (this.width < 1)
			this.width = 2;
		if (this.height < 1)
			this.height = 2;
	}
	
	public void zoomOut() {
		this.width*=1.25;
		this.height*=1.25;
	}
	
	//if the camera is trying to show something off the map then it stops it.
	public void adjust() {
		
	}
	
	public void setEntityToFocus(Entity e) {
		this.entityToFocus = e;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void move(double dx, double dy) {
		this.x+=dx;
		this.y+=dy;
	}
	
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
