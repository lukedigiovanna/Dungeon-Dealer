package mapping.rooms;

import mapping.Grid;

public class SampleLootRoom extends Room {
	public SampleLootRoom() {
		super();
		setGrid(Grid.parseStringToGrid(
			"  wwwww   & wlllllw  & wlwlwlw  & wlllllw  & wlwlwlw  & wlllllw  &  wwwww   &"
		));
		setTierID(2);
	}
}
