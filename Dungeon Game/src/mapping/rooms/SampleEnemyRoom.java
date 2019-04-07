package mapping.rooms;

import mapping.*;

public class SampleEnemyRoom extends Room {
	public SampleEnemyRoom() {
		super();
		Grid g = Grid.parseStringToGrid(
				" wwwwwww &wwlllllww&wlllllllw&wlllllllw&wlllllllw&wlllllllw&wlllllllw&wwlllllww& wwwwwww "
		);
		setGrid(g);		
		setTierID(0);
	}
}
