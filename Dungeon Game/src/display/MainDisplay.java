package display;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import console.Console;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Driver;

public class MainDisplay extends Display {
	public MainDisplay(JPanel panel) {
		this(Driver.WIDTH,Driver.HEIGHT, panel);
	}
	
	//main constructor
	public MainDisplay(int width, int height, JPanel panel) {
		super(width,height,panel);
		Button b = new Button("Play",Driver.WIDTH/2,Driver.HEIGHT/3);
		b.setFont(new Font("Serif",Font.BOLD,26));
		b.setBackgroundColor(Color.GRAY);
		b.setTextColor(Color.LIGHT_GRAY);
		b.setMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (b.inBox(e))
					DisplayController.setDisplay(DisplayController.DISPLAY_GAME);
			}
		});
		addButton(b);
		Button b2 = new Button("Update Notes",Driver.WIDTH/2,Driver.HEIGHT/3+35);
		b2.setFont(new Font("Serif",Font.BOLD,26));
		b2.setBackgroundColor(Color.GRAY);
		b2.setTextColor(Color.LIGHT_GRAY);
		b2.setMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (b2.inBox(e)) 
					DisplayController.setDisplay(DisplayController.DISPLAY_UPDATE_NOTES);
			}
		});
		addButton(b2);
		Button b4 = new Button("Room Map Test",Driver.WIDTH/2,Driver.HEIGHT/3+70);
		b4.setFont(new Font("Serif",Font.BOLD,26));
		b4.setBackgroundColor(Color.GRAY);
		b4.setTextColor(Color.LIGHT_GRAY);
		b4.setMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (b4.inBox(e)) 
					DisplayController.setDisplay(DisplayController.DISPLAY_ROOM_TEST);
			}
		});
		addButton(b4);
		Button b3 = new Button("Quit",Driver.WIDTH/2,Driver.HEIGHT/3+105);
		b3.setFont(new Font("Serif",Font.BOLD,26));
		b3.setBackgroundColor(Color.GRAY);
		b3.setTextColor(Color.LIGHT_GRAY);
		b3.setMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (b3.inBox(e)) 
					System.exit(250);
			}
		});
		addButton(b3);
		
	}
	
	//inherited from the Display superclass
	//handles all the drawing methods to the buffered image.
	//this image will be drawn onto a JPanel later
	@Override
	public void draw() {
		Graphics g = this.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		ImageIcon img = new ImageIcon("lib/assets/images/background.jpg");
		g.drawImage(img.getImage(), 0, 0, this.getWidth(),this.getHeight(), null);
		g.setFont(new Font("Vivaldi", Font.BOLD, 48));
		int width = g.getFontMetrics().stringWidth(main.Information.GAME_NAME);
		g.setColor(Color.WHITE);
		g.drawString(main.Information.GAME_NAME, this.getWidth()/2-width/2, this.getHeight()/4);
		super.draw();
		drawButtons();
	}
	
	//inherited from the Display superclass
	//check the superclass for extra comments.
	int rgb = 0;
	public void update() {
		rgb++;
		draw();
		//Console.log(new console.Message("reee",new Color(rgb));
	}
}
