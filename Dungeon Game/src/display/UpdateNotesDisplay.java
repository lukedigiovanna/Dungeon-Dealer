package display;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class UpdateNotesDisplay extends Display {
	public UpdateNotesDisplay(JPanel panel) {
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
	}

	public void draw() {
		Graphics g = this.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.GREEN);
		g.setFont(new Font("Cordova",Font.BOLD,28));
		g.drawString("Update Notes For Version "+main.Information.VERSION, getWidth()/2-g.getFontMetrics().stringWidth("Update Notes For Version "+main.Information.VERSION)/2,getHeight()/8);
		String[] notes = {
				"TODO:",
				"   Work on room setup stuff"
		};
		g.setColor(Color.WHITE);
		g.setFont(new Font("Cordova",Font.PLAIN,15));
		int startY = getHeight()/8+50;
		for (int i = 0; i < notes.length; i++) {
			int length = 0;
			while (notes[i].charAt(length) == ' ')
				length++;
			String whiteSpace = "";
			for (int j = 0; j < length; j++)
				whiteSpace+=" ";
			String toDraw = notes[i].substring(length);
			g.drawString(whiteSpace+"• "+toDraw, 100, i*g.getFontMetrics().getHeight()+startY);
		}
		super.draw();
		this.drawButtons();
	}
	
	@Override
	public void update() {
		draw();
	}
}
