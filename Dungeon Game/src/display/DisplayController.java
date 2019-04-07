package display;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import console.Console;

//static class used to determine which display to display

public class DisplayController {
	public final static int DISPLAY_MAIN = 0, DISPLAY_GAME = 1, DISPLAY_PAUSE = 2, DISPLAY_UPDATE_NOTES = 3, DISPLAY_ROOM_TEST = 4;
	public static MainDisplay main;
	public static GameDisplay game;
	public static PauseMenuDisplay pauseMenu;
	public static UpdateNotesDisplay updateNotes;
	public static RoomMapTestDisplay roomTest;
	private static JPanel panel;
	private static Display display;
	
	public static void initialize(JPanel aPanel) {
		//this method must be called before actually handling any of the displays
		panel = aPanel;
		main = new MainDisplay(panel);
		game = new GameDisplay(panel);
		pauseMenu = new PauseMenuDisplay(panel);
		updateNotes = new UpdateNotesDisplay(panel);
		roomTest = new RoomMapTestDisplay(panel);
	}
	
	public static void setDisplay(int id) {
		switch (id) {
		case DISPLAY_MAIN:
			setDisplay(main);
			break;
		case DISPLAY_GAME:
			setDisplay(game);
			game.resumeThreads();
			break;
		case DISPLAY_PAUSE:
			setDisplay(pauseMenu);
			break;
		case DISPLAY_UPDATE_NOTES:
			setDisplay(updateNotes);
			break;
		case DISPLAY_ROOM_TEST:
			setDisplay(roomTest);
		}
	}
	
	public static void setDisplay(Display aDisplay) {
		//before the display is actually changed, modify the previous display
		if (aDisplay == null)
			return;
		if (display != null) 
			display.removeListeners();
		display = aDisplay;
		display.addListeners();
	}
	
	public static Display getDisplay() {
		return display;
	}
	
	public static void drawCurrentDisplay(Graphics g, JPanel panel) {
		//draws the current display on a jpanel using its graphics.
		g.drawImage(display,0,0,panel.getWidth(),panel.getHeight(),null);
	}
	
	public static void updateCurrentDisplay() {
		display.update();
	}
}
