package mapping.rooms;

import mapping.*;

public class BeginningRoom extends Room {
	public BeginningRoom() {
		super();
		Grid g = Grid.parseStringToGrid(
				  " wwlww &"
				+ "wwlllww&"
				+ "wlllllw&"
				+ "lllllll&"
				+ "wlllllw&"
				+ "wwlllww&"
				+ " wwlww &");
		setGrid(g);
		setTierID(0);
	}
}
