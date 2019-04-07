package display;

import javax.swing.JPanel;

import game.GameManager;
import mapping.Grid;
import mapping.RoomMap;
import mapping.gridComponents.GridComponent;

import java.awt.*;
import java.awt.event.*;

public class RoomMapTestDisplay extends Display {
	public RoomMapTestDisplay(JPanel panel) {
		super(panel);
		Button b = new Button("Go Back",70,30);
		b.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,26));
		b.setMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (b.inBox(e))
					DisplayController.setDisplay(DisplayController.DISPLAY_MAIN);
			}
		});
		addButton(b);
		
		Button b2 = new Button("Change seed", 70,70);
		b2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,26));
		b2.setMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (b2.inBox(e))
					newSeed();
			}
		});
		addButton(b2);
		
		rm = new RoomMap(862743);
		arr = rm.getCharMap();
	}
	
	private char[][] arr;
	private RoomMap rm;

	public void newSeed() {
		rm = new RoomMap((int)(Math.random()*900000+100000));
		arr = rm.getCharMap();
	}
	
	public void draw() {
		Graphics g = this.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//draw the room map
		int xOff = 50;
		int yOff = 200;
		g.setColor(Color.WHITE);
		g.drawString("Seed: "+rm.getSeed(), xOff, yOff-20);
		for (int x = 0; x < arr.length; x++) 
			for (int y = 0; y < arr[x].length; y++) {
				g.drawRect(x*32+xOff, y*32+yOff, 32, 32);
				g.drawString(arr[x][y]+"", x*32+12+xOff, y*32+16+yOff);
			}
		
		Grid grid = rm.getGrid();
		GridComponent[][] gm = (grid != null) ? grid.getGrid() : null;
		//draw the array
		if (gm != null)
			for (int x = 0; x < gm.length; x++)
				for (int y = 0; y < gm[x].length; y++)
					if (gm[x][y] != null)
						g.drawImage(gm[x][y].getRawImage(), x*8+xOff+180, y*8+50, 8, 8, null);
		
		super.draw();
		this.drawButtons();
	}
	
	@Override
	public void update() {
		draw();
	}
}
