package mapping;

import java.awt.*;
import java.awt.image.BufferedImage;
import mapping.gridComponents.*;
import mapping.gridComponents.Void;

//contains the 2d grid (array) that corresponds to the world position
//the 'map' is a grid with origins in the top left of the world at 0,0.
public class Grid {
	private GridComponent[][] grid;
	
	public Grid() {
		this(0,0); //default
	}
	
	public Grid(int width, int height) {
		grid = new GridComponent[width][height];
	}
	
	//adds a GridComponent at a specified x, y on the 2D array
	public void add(GridComponent comp, int x, int y) {
		grid[x][y] = comp; 
	}
	
	public GridComponent get(int x, int y) {
		if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
			return grid[x][y];
		return null;
	}
	
	//actually adds another grid
	public void add(Grid grid, int x, int y) {
		//modfies the grid that calls this method
		int width = Math.abs(x)+grid.getWidth();
		if (x < 0)
			width = Math.abs(x)+this.getWidth();
		if (width < this.getWidth())
			width = this.getWidth();
		
		int height = Math.abs(y)+grid.getHeight();
		if (y < 0)
			height = Math.abs(y)+this.getHeight();
		if (height < this.getHeight())
			height = this.getHeight();
		Grid newGrid = new Grid(width,height);
		int xOffset = 0;
		if (x < 0)
			xOffset = Math.abs(grid.getWidth());
		int yOffset = 0;
		if (y < 0)
			yOffset = Math.abs(grid.getHeight())-1;
		//add all old grid components
		for (int gx = 0; gx < this.getWidth(); gx++) 
			for (int gy = 0; gy < this.getHeight(); gy++) 
				newGrid.getGrid()[gx+xOffset][gy+yOffset] = this.grid[gx][gy];
				
		int startX = x;
		if (startX < 0)
			startX = 0;
		int startY = y;
		if (startY < 0)
			startY = 0;
		for (int gx = startX; gx < grid.getWidth()+startX; gx++)
			for (int gy = startY; gy < grid.getHeight()+startY; gy++) 
				newGrid.getGrid()[gx][gy] = grid.getGrid()[gx-startX][gy-startY];
		this.grid = newGrid.getGrid();
	}
	
	//adds a however many columns to the end of array. maintains the same gridcomponents from before
	public void addColumns(int howMany) {
		GridComponent[][] newGrid = new GridComponent[grid.length+howMany][grid[0].length];
		for (int i = 0; i < grid.length; i++) 
			newGrid[i] = grid[i];
		grid = newGrid;
	}
	
	public void addRows(int howMany) {
		GridComponent[][] newGrid = new GridComponent[grid.length][grid[0].length+howMany];
		for (int x = 0; x < grid.length; x++) 
			for (int y = 0; y < grid[x].length; y++) 
				newGrid[x][y] = grid[x][y];
		grid = newGrid;
	}
	
	public static Grid parseStringToGrid(String str) {
		if (str == null)
			return null;
		Grid newGrid = new Grid();
		str = str.toLowerCase();
		String[] rows = str.split("&");
		int width = rows[0].length();
		int height = rows.length;
		newGrid.setGrid(new GridComponent[width][height]);
		for (int y = 0; y < rows.length; y++) {
			for (int x = 0; x < rows[y].length(); x++) {
				GridComponent g = GridComponent.parseChar(rows[y].charAt(x));
				newGrid.add(g,x,y);
			}
		}
		return newGrid;
	}
	
	public void fillNulls() {
		//asigns all the grid points of the level to be void
		for (int x = 0; x < grid.length; x++) 
			for (int y = 0; y < grid[0].length; y++) 
				if (grid[x][y] == null) 
					grid[x][y] = new Void();
	}
	
	public GridComponent[][] getGrid() {
		return grid;
	}
	
	public void setGrid(GridComponent[][] grid) {
		this.grid = grid;
	}
	
	public int getWidth() {
		return grid.length;
	}
	
	public int getHeight() {
		if (grid.length < 1)
			return 0;
		return grid[0].length;
	}
}
