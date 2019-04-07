package game;

import display.*;

public class GameManager {
	public static Game game;
	
	public static void init() {
		game = new Game(DisplayController.game);
		game.init();
	}
	
	public static void initListeners() {
		game.initListeners();
	}
	
	public static void pause() {
		game.pauseThreads();
	}
}
