package mapping;

import java.awt.image.BufferedImage;
import java.util.*;
import entities.players.Player;
import entities.Entity;

//the dungeon holds all the information for the dungeon that is it holds the levels which hold maps and entities and that stuff
//the game class should handle other things like drawing. this class should only be used to access that data, not for doing
//any drawing
public class Dungeon {
	private ArrayList<Level> levels = new ArrayList<Level>(0);
	private int currentLevelNum = 0;
	private int seed;
	
	public Dungeon() {
		this((int)(Math.random()*900000)+100000);
	}
	
	public Dungeon(int seed) {
		this.seed = seed;
		this.addLevel(new Level(seed));
	}
	
	public BufferedImage getCurrentCameraView() {
		return getLevel().getCamera().getView();
	}
	
	public void addLevel(Level level) {
		levels.add(level);
	}
	
	public ArrayList<Level> getLevels() {
		return levels;
	}
	
	public Level getLevel(int elementID) {
		return levels.get(elementID);
	}
	
	public Level getLevel() {
		return getLevel(currentLevelNum);
	}
	
	public Grid getLevelGrid() {
		return getLevel().getGrid();
	}
	
	public Player getPlayer() {
		for (Entity e : Entity.entities)
			if (e instanceof Player)
				return (Player)e;
		return null;
	}
	
	public int getSeed() {
		return seed;
	}
	
	public void setSeed(int seed) {
		this.seed = seed;
	}
	
	public int getLevelNumber() {
		return currentLevelNum+1;
	}
}
