package display;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import entities.Entity;
import main.Driver;

public class PauseMenuDisplay extends Display {

	public PauseMenuDisplay(JPanel panel) {
		super(panel);
		Button b = new Button("Play", this.getWidth()/2,this.getHeight()/2-this.getHeight()/5);
		b.setFont(new Font("Courier",Font.BOLD,26));
		b.setBackgroundColor(Color.LIGHT_GRAY);
		b.setTextColor(Color.DARK_GRAY);
		b.setMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (b.inBox(e)) {
					DisplayController.setDisplay(DisplayController.DISPLAY_GAME);
				}
			}
		});
		addButton(b);
		Button b2 = new Button("Main Menu", this.getWidth()/2,this.getHeight()/2-this.getHeight()/8);
		b2.setFont(new Font("Courier",Font.BOLD,26));
		b2.setBackgroundColor(Color.LIGHT_GRAY);
		b2.setTextColor(Color.DARK_GRAY);
		b2.setMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (b2.inBox(e)) {
					DisplayController.setDisplay(DisplayController.DISPLAY_MAIN);
				}
			}
		});
		addButton(b2);
	}
	
	@Override
	public void addListeners() {
		super.addListeners(); //add the listeners from the super class as well
		getParentPanel().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					DisplayController.setDisplay(DisplayController.DISPLAY_GAME);
				}
			}
		});
	}

	@Override
	public void draw() {
		Graphics g = this.getGraphics();
		g.setColor(Color.RED);
		int borderWidth = 10;
		int topLeftX = this.getWidth()/4-borderWidth;
		int topLeftY = this.getHeight()/4-borderWidth;
		int width = this.getWidth()/2;
		int height = this.getHeight()/2;
		g.fillRect(topLeftX, topLeftY, width, borderWidth);
		g.fillRect(topLeftX, topLeftY, borderWidth, height);
		g.fillRect(topLeftX+width-borderWidth, topLeftY, borderWidth, height);
		g.fillRect(topLeftX, topLeftY+height-borderWidth, width, borderWidth);
		drawButtons();
	}

	@Override
	public void update() {
		draw();
	}
}
