package mapping.roomDesigner;

import javax.swing.*;
import java.awt.*;
import mapping.roomDesigner.Panel;

public class RoomDesigner {
	public static JFrame frame;
	public static void main(String[] args) {
		frame = new JFrame("Room Designer");
		frame.setSize(600, 600);
		frame.setLocation(100,50);
		frame.requestFocus();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Panel());
		frame.setVisible(true);
	}
}
