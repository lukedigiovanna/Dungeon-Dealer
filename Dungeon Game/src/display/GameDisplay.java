package display;

import javax.swing.JPanel;
import main.*;
import mapping.*;
import game.*;
import console.*;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;

public class GameDisplay extends Display {
	public GameDisplay(JPanel panel) {
		this(Driver.WIDTH,Driver.HEIGHT,panel);
	}
	
	public GameDisplay(int width, int height, JPanel panel) {
		super(width,height,panel);
		Button b = new Button("PAUSE",Driver.WIDTH-100,Driver.HEIGHT-50);
		b.setFont(new Font("Vivaldi",Font.BOLD,26));
		b.setMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (b.inBox(e))
					pauseGame();
			}
		});
	}
	
	public void pauseGame() {
		GameManager.pause();
		DisplayController.setDisplay(DisplayController.DISPLAY_PAUSE);
	}
	
	public void pauseThreads() {
		GameManager.game.pauseThreads();
	}
	
	public void resumeThreads() {
		GameManager.game.resumeThreads();
	}
	
	@Override 
	public void addListeners() {
		super.addListeners();
		if (GameManager.game != null)
			GameManager.game.initListeners();
		getParentPanel().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					pauseGame();
					break;
				}
			}
		});
	}

	@Override
	public void draw() {
		GameManager.game.draw();
		super.draw();
		drawButtons(); //draw the buttons on top of the display
	}

	@Override
	public void update() {
		draw();
	}
}
