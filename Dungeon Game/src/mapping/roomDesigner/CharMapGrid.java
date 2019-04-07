package mapping.roomDesigner;

public class CharMapGrid {
	private char[][] grid;
	
	public CharMapGrid() {
		grid = new char[10][10];
		for (int x = 0; x < grid.length; x++) 
			for (int y = 0; y < grid[x].length; y++)
				grid[x][y] = '*';
	}
	
	public void setPoint(char s, int x, int y) {
		grid[x][y] = s;
	}
	
	public char[][] getGrid() {
		return grid;
	}
	
	public String getString() {
		int width = getWidth();
		int height = getHeight();
		String s = "";
		for (int y = firstY; y <= lastY; y++) {
			for (int x = firstX; x <= lastX; x++) {
				char toAdd = grid[x][y];
				if (toAdd == '*')
					toAdd = ' ';
				s+=toAdd;
			}
			s+='&';
		}
		return s;
	}
	
	private int firstX, lastX, firstY, lastY;
	
	public int getWidth() {
		firstX = grid.length;
		lastX = 0;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y] != '*') {
					if (x <= firstX)
						firstX = x;
					if (x >= lastX)
						lastX = x;
				}
			}
		}
		int width = lastX-firstX;
		if (!(firstX == 0 && lastX == 0))
			width++;
		if (width < 0)
			width = 0;
		return width;
	}
	
	public int getHeight() {
		firstY = grid[0].length;
		lastY = 0;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y] != '*') {
					if (y <= firstY)
						firstY = y;
					if (y >= lastY)
						lastY = y;
				}
			}
		}
		int height = lastY-firstY;
		if (!(firstY == 0 && lastY == 0))
			height++;
		if (height < 0)
			return 0;
		return height;
	}
}
