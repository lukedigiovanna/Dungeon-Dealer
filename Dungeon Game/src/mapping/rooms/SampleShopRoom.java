package mapping.rooms;

import mapping.Grid;

public class SampleShopRoom extends Room {
	public SampleShopRoom() {
		super();
		setGrid(Grid.parseStringToGrid(
				  "  www  & wlllw &wlllllw&wlllllw&wlllllw&wlllllw&wwwwwww&"
		));
		setTierID(2);
	}
}
