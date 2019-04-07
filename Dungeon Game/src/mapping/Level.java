package mapping;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.*;

public class Level {
	private Grid grid;
	private Camera camera;
	private int levelNum = 0;
	private RoomMap roomMap;
	
	//constructors
	public Level(int seed) {
		roomMap = new RoomMap(seed);
		grid = roomMap.getGrid();
		int width = grid.getWidth();
		int height = grid.getHeight();
		int cWidth = 18;
		int cHeight = 12;
		double x = -(cWidth-width)/2;
		double y = -(cHeight-height)/2;
		camera = new Camera(x, y, cWidth, cHeight, this);
		
	}
	
	public Level(Grid grid) {
		this.roomMap = null;
		this.grid = grid;
		int width = grid.getWidth();
		int height = grid.getHeight();
		int cWidth = 18;
		int cHeight = 12;
		double x = -(cWidth-width)/2;
		double y = -(cHeight-height)/2;
		camera = new Camera(x, y, cWidth, cHeight, this);
	}
	
	//getter / setters
	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}
	
	public RoomMap getRoomMap() {
		return roomMap;
	}
}
