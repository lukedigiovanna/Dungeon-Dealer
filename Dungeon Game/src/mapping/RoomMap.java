package mapping;

import mapping.gridComponents.*;
import mapping.gridComponents.Void;
import mapping.rooms.*;

public class RoomMap {
	private Grid grid;
	private Room[][] roomMap;
	private char[][] charMap;
	private int width = 8, height = 8;
	private int numOfRooms;
	private double proximityOfRooms = 0.5; //0 is really close, 1 is really far apart
	private int seed;
	
	public RoomMap(int seed) {
		this.seed = seed;   
		this.generate();
	}
	
	public void generate() {
		//always start by putting a beginning room at the center
		console.Console.log("<s=t>ROOM MAP INFORMATION");
		//make the rooms
		numOfRooms = seed%100/10+5;
		console.Console.log("Num of Rooms: <c=r>"+numOfRooms);
		width = height = (int)(numOfRooms*proximityOfRooms);
		if (width*height < numOfRooms) 
			width=height=(int)(Math.sqrt(numOfRooms)+1);
		if (width % 2 == 0) {
			width++;
			height++;
		}
		console.Console.log("Dimensions: <c=r>"+width+" x "+height);
		roomMap = new Room[width][height];
		charMap = new char[width][height];
		for (int x = 0; x < charMap.length; x++) 
			for (int y = 0; y < charMap[x].length; y++)
				charMap[x][y] = ' ';
		int centerX = width/2;
		int centerY = height/2;
		console.Console.log("Center XY: <c=r>"+centerX+", "+centerY);
		charMap[centerX][centerY] = 'b';
		numOfRooms--;
		
		double maxDist = Math.sqrt((width-centerX)*(width-centerX)+(height-centerY)*(height-centerY));
		double distInterval = maxDist/3;
		int x = (int)(random()*width);
		int y = (int)(random()*height);
		//numOfRooms = width*height-1;
		char[] charRooms = {'l','e','s'};
		int[] tierLevels = {1, 0, 1};
		int[] maxFrequencies = {1, 999, 1};
		int[] frequencyCounts = {0, 0, 0};
		for (int i = 0; i < numOfRooms; i++) {
			while (charMap[x][y] != ' ') {
				x = (int)(random()*width);
				y = (int)(random()*height);
			}
			double distanceToCenter = Math.sqrt((x-centerX)*(x-centerX)+(y-centerY)*(y-centerY));
			int tierLevel = (int)(distanceToCenter/distInterval);
			int index = (int)(random()*charRooms.length);
			int attempts = 0;
			while (tierLevels[index] > tierLevel || frequencyCounts[index] >= maxFrequencies[index]) {
				index = (int)(random()*charRooms.length);
				attempts++;
				if (attempts > 1000)
					continue;
			}
			frequencyCounts[index]++;
			charMap[x][y] = charRooms[index];
		}
		
		makeRoomMap();
		
		generateGrid();
		
		console.Console.log("");
	}
	
	private double random() {
		seed = (int)(Math.abs(Math.cos(seed))*seed*2);
		//set limits to prevent the number from diverging or getting too large for the computer to handle
		if (seed > 100000000)
			seed/=2;
		if (seed < 20000)
			seed*=5;
		double num = Math.abs((double)seed);
		num = Math.abs(Math.cos(num)); //uses cos to get a value between 0 and 1 (inclusive)
		return num;
	}
	
	public char[][] getCharMap() {
		return this.charMap;
	}
	
	private void makeRoomMap() {
		//loop through the char map and make a new room of the specified type
		for (int x = 0; x < charMap.length; x++) 
			for (int y = 0; y < charMap[x].length; y++) {
				switch (charMap[x][y]) {
				case 'b':
					roomMap[x][y] = new BeginningRoom();
					break;
				case 'e':
					roomMap[x][y] = new SampleEnemyRoom(); //temporary 
					break;
				case 'l':
					roomMap[x][y] = new SampleLootRoom();
					break;
				case 's':
					roomMap[x][y] = new SampleShopRoom();
				}
			}
	}
	
	private void connectRooms() {
		for (int x = 0; x < roomMap.length; x++)
			for (int y = 0; y < roomMap[x].length; y++) 
				if (roomMap[x][y] != null)
					makeCrossroads(x*getSize()+getSize()/2-1,y*getSize()+getSize()/2-1);
	}
	
	private void makeCrossroads(int x, int y) {
		//set the limits
		GridComponent[][] g = grid.getGrid();
		int gWidth = g.length;
		int gHeight = g[0].length;
		if (x < 0 || y < 0 || x >= gWidth || y >= gHeight)
			return;
		
		for (int x1 = 0; x1 < gWidth; x1++) {
			int y1 = y;
			if (g[x1][y1] == null || g[x1][y1] instanceof Wall || g[x1][y1] instanceof Void) {
				g[x1][y1] = new PlanksPath();
				g[x1][y1-1] = new CobblestoneWall();
				g[x1][y1+1] = new CobblestoneWall();
			}
		}
		
		for (int y1 = 0; y1 < gHeight; y1++) {
			int x1 = x;
			if (g[x1][y1] == null || g[x1][y1] instanceof Wall || g[x1][y1] instanceof Void) {
				g[x1][y1] = new PlanksPath();
				g[x1-1][y1] = new CobblestoneWall();
				g[x1+1][y1] = new CobblestoneWall();
			}
		}
	}
	
	//returns the  width/height of a room on the map
	private int getSize() {
		int max = 0;
		for (int x = 0; x < roomMap.length; x++)
			for (int y = 0; y < roomMap[x].length; y++) {
				if (roomMap[x][y] == null)
					continue;
				if (roomMap[x][y].getGrid().getWidth() > max)
					max = roomMap[x][y].getGrid().getWidth();
				if (roomMap[x][y].getGrid().getHeight() > max)
					max = roomMap[x][y].getGrid().getHeight();
			}
		return max;
	}
	
	
	public void generateGrid() {
		int size = getSize()*width;
		int scale = getSize();
		grid = new Grid(size,size);
		for (int x = 0; x < roomMap.length; x++) 
			for (int y = 0; y < roomMap[x].length; y++)
				if (roomMap[x][y] != null) {
					Grid g = roomMap[x][y].getGrid();
					int width = g.getWidth();
					int height = g.getHeight();
					int xOff = (scale-width)/2;
					int yOff = (scale-height)/2;
					grid.add(roomMap[x][y].getGrid(), x*scale+xOff, y*scale+yOff);
				}
		connectRooms();
		//fillVoid();
	}
	
	private void fillVoid() {
		for (int x = 0; x < grid.getWidth(); x++)
			for (int y = 0; y < grid.getHeight(); y++)
				if (grid.get(x,y) == null || grid.get(x, y) instanceof Void)
					grid.getGrid()[x][y] = new CobblestoneWall();
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public int getSeed() {
		return seed;
	}
}
