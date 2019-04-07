package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import console.Console;
import console.Message;
import console.MessageBox;
import main.Driver;
import main.Information;

public abstract class Display extends BufferedImage {
	private JPanel parentPanel;
	private ButtonList buttonList;
	
	public Display(JPanel panel) {
		this(Driver.WIDTH,Driver.HEIGHT,panel);
	}
	
	public Display(int width, int height, JPanel panel) {
		super(width,height,BufferedImage.TYPE_INT_ARGB);
		this.parentPanel = panel;
		buttonList = new ButtonList();
	}
	
	public void draw() {
		Graphics g = this.getGraphics();
		g.drawImage(Console.getImage(), 0, getHeight()/2,getWidth()/2,getHeight()/2,null);
		g.drawImage(Information.getImage(), getWidth()-Information.getImage().getWidth()-10, getHeight()-Information.getImage().getHeight(),Information.getImage().getWidth(),Information.getImage().getHeight(),null);
	}
	
	//is called when this is the set display on the display controller
	//every time the DisplayController.updateCurrentDisplay() method is called.
	//that method is called in the main package in the Panel class
	//handles any event the display needs to several times a second
	public abstract void update();

	public JPanel getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(JPanel parentPanel) {
		this.parentPanel = parentPanel;
	}
	
	public void addButton(Button b) {
		buttonList.add(b);
		b.setParentDisplay(this);
	}
	
	public void addListeners() {
		buttonList.addMouseListeners(parentPanel);
		//most key listeners will be added to the panel in some other class
		getParentPanel().addKeyListener(Console.getBox().keyboard);
		getParentPanel().addKeyListener(Console.getBox().activation);
		getParentPanel().addMouseWheelListener(Console.getBox().scroll);
	}
	
	public void removeListeners() {
		MouseListener[] mouseListeners = parentPanel.getMouseListeners();
		KeyListener[] keyListeners = parentPanel.getKeyListeners();
		for (MouseListener listener : mouseListeners) 
			parentPanel.removeMouseListener(listener);
		for (KeyListener listener : keyListeners)
			parentPanel.removeKeyListener(listener);
	}
	
	public void drawButtons() {
		buttonList.drawButtons(this.getGraphics());
	}
}
