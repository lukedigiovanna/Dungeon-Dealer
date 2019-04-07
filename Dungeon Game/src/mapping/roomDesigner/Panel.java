package mapping.roomDesigner;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import mapping.gridComponents.*;
import mapping.gridComponents.Void;

public class Panel extends JPanel {
	private CharMapGrid grid = new CharMapGrid();
	private Color backgroundColor = Color.LIGHT_GRAY;
	private char c = 'w';
	JComboBox options;
	
	public Panel() {
		this.setFocusable(true);
		JButton getString = new JButton("Get String");
		getString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog(null, "", grid.getString());
			}
		});
		add(getString);
		JButton showInstructions = new JButton("Show Instructions");
		showInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, 
						"Press a key to change what\n"
						+ "component you are adding\n"
						+ "\n"
						+ "Click on the grid to change\n"
						+ "the component to the one\n"
						+ "you have chosen\n"
						+ "\n"
						+ "Export your grid by clicking the\n"
						+ "'Get String' button"
						);
			}
		});
		String[] optionsString = {"Void", "Wall", "Planks", "Path"};
		options = new JComboBox(optionsString);
		options.setSelectedIndex(1);
		add(options);
		
		add(showInstructions);
		addMouseListener(new Mouse());
		this.addMouseMotionListener(new MouseMotion());
		addKeyListener(new Key());
		Thread t = new Thread(new updateRunnable());
		t.start();
	}
	
	public void paintComponent(Graphics g) {
		switch (options.getSelectedItem().toString()) {
		case "Void":
			c = ' ';
			break;
		case "Wall":
			c = 'w';
			break;
		case "Planks":
			c = 'l';
			break;
		case "Path":
			c = 'p';
		}
		size = 40;
		sWidth = grid.getGrid().length*size;
		sHeight = grid.getGrid()[0].length*size;
		sx = getWidth()/2-sWidth/2;
		sy = getHeight()/2-sHeight/2;
		g.setColor(backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		//draw the grid
		char[][] sGrid = grid.getGrid();
		for (int x = 0; x < sGrid.length; x++) {
			for (int y = 0; y < sGrid[x].length; y++) {
				GridComponent c = GridComponent.parseChar(sGrid[x][y]);
				if (c == null)
					c = new Void();
				while (!c.getExtras().isEmpty())
					c.getExtras().remove(0);
				g.setColor(Color.BLACK);
				g.fillRect(sx+x*size-1,sy+y*size-1,size+2,size+2);
				g.setColor(backgroundColor);
				g.fillRect(sx+x*size, sy+y*size, size, size);
				g.drawImage(c.getImage(), sx+x*size, sy+y*size, size, size, null);
			}
		}
		GridComponent comp = GridComponent.parseChar(c);
		g.drawImage(comp.getImage(), getWidth()-size, 0, size, size, null);
	}
	
	public void update() {
		repaint();
	}
	
	int size = 50;
	int sWidth = grid.getGrid().length*size;
	int sHeight = grid.getGrid()[0].length*size;
	int sx = getWidth()-sWidth/2, sy = getHeight()-sHeight/2;
	
	private class MouseMotion extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int gx = (x-sx)/size;
			int gy = (y-sy)/size;
			try {
				grid.getGrid()[gx][gy] = c;
			} catch (Exception ex) {
				//do nothing
			}
			if (SwingUtilities.isRightMouseButton(e)) 
				grid.getGrid()[gx][gy] = '*';
		}
	}
	
	private class Mouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int gx = (x-sx)/size;
			int gy = (y-sy)/size;
			try {
				grid.getGrid()[gx][gy] = c;
			} catch (Exception ex) {
				//do nothing
			}
			if (SwingUtilities.isRightMouseButton(e)) 
				grid.getGrid()[gx][gy] = '*';
		}
	}
	
	private class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			c = e.getKeyChar();
			System.out.println(c);
		}
	}
	
	private class updateRunnable implements Runnable {
		public void run() {
			while (true) {
				try {
					update();
					Thread.sleep(50);
				} catch (Exception e) {
				}
			}
		}
	}
}
