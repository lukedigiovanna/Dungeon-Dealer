package mapping.rooms;

import mapping.*;

public abstract class Room {
	private Grid grid;
	private int tierID = 0;
	
	public Room() {
		
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public int getTierID() {
		return tierID;
	}

	public void setTierID(int tierID) {
		this.tierID = tierID;
	}
}
