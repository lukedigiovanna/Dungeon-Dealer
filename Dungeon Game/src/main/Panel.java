
package main;

import display.*;
import entities.Entity;
import game.GameManager;

import java.awt.*;

import javax.swing.*;

import data.*;

//a very general class to just handle displaying and updating displays
public class Panel extends JPanel {
	private FPSCounter fpsCounter;
	public static int maxFPS = 20;
	
	public Panel() {
		Data.init();
		this.setFocusable(true);
		Entity.init();
		DisplayController.initialize(this);
		DisplayController.setDisplay(DisplayController.DISPLAY_MAIN);
		GameManager.init();
		fpsCounter = new FPSCounter();
		fpsCounter.start();
		Gui gui = new Gui();
		Thread thread = new Thread(gui);
		thread.start();
	}
	
	//is called by the gui class every 50ms.. handles game logic and display
	public void update() {
		DisplayController.updateCurrentDisplay();
		this.repaint(); //tells the paintComponent() method to run again.
		fpsCounter.interrupt();
	}
	
	public double getFPS() {
		return fpsCounter.fps();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		DisplayController.drawCurrentDisplay(g, this);
	}
	
	private class Gui implements Runnable {
		public void run() {
			while (true) {
				update();
				try {
					Thread.sleep(1000/maxFPS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
