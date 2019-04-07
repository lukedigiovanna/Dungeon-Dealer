package display;

import javax.swing.JPanel;
import java.util.*;
import java.util.List;
import java.awt.*;

public class ButtonList {
	private List<Button> buttons = new ArrayList<Button>();
	
	public ButtonList() {
		
	}
	
	public void add(Button button) {
		buttons.add(button);
	}
	
	public void addMouseListeners(JPanel parentPanel) {
		for (Button button : buttons) 
			parentPanel.addMouseListener(button.getMouseAdapter());
	}
	
	public void drawButtons(Graphics g) {
		for (Button button : buttons) 
			button.draw(g);
	}
}
