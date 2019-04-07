package display;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Button {
	private MouseListener ml;
	private String text;
	//x, y is the center of the button
	private int imageX, imageY;
	private int width = -1, height = -1;
	private Color backgroundColor;
	private Color textColor;
	private Font textFont;
	private Display parentDisplay;
	
	//just coordinates
	public Button(int x, int y) {
		this("",x,y);
	}
	
	//essentially a clickable box
	public Button(int x, int y, int width, int height) {
		this("",x,y,width,height);
	}
	
	//a button with a defined width and height
	public Button(String text, int x, int y, int width, int height) {
		this(text,x,y);
		this.width = width;
		this.height = height;
	}
	
	//the width and height will be adjusted to the string size as long as the width and height aren't set
	public Button(String text, int x, int y) {
		this.text = text;
		this.imageX = x;
		this.imageY = y;
		fixNulls();
	}
	
	public void setMouseListener(MouseListener ml) {
		this.ml = ml;
	}
		
	public MouseListener getMouseAdapter() {
		return this.ml;
	}
	
	public void setParentDisplay(Display pd) {
		this.parentDisplay = pd;
	}
	
	public boolean inBox(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		int modWidth = getDrawnWidth();
		int modHeight = getDrawnHeight();
		if (x > getPanelX()-getDrawnPanelWidth()/2 && x < getPanelX()+getDrawnPanelWidth()/2 && y < getPanelY()+getDrawnPanelHeight()/2 && y > getPanelY()-getDrawnPanelHeight()/2) 
			return true;
		return false;
	}
	
	public int getPanelX() {
		double scaleFactor = getWidthScaleFactor();
		int x = (int)(scaleFactor*imageX);
		return x;
	}
	
	public int getPanelY() {
		double scaleFactor = getHeightScaleFactor();
		int y = (int)(scaleFactor*imageY);
		return y;
	}
	
	public int getDrawnPanelWidth() {
		return (int)(getDrawnWidth()*getWidthScaleFactor());
	}
	
	public int getDrawnPanelHeight() {
		return (int)(getDrawnHeight()*getHeightScaleFactor());
	}
	
	private double getWidthScaleFactor() {
		double sf = (double)parentDisplay.getParentPanel().getWidth()/parentDisplay.getWidth();
		return sf;
	}
	
	private double getHeightScaleFactor() {
		double sf = (double)parentDisplay.getParentPanel().getHeight()/parentDisplay.getHeight();
		return sf;
	}
	
	public void fixNulls() {
		if (backgroundColor == null)
			backgroundColor = Color.WHITE; //default color
		if (textColor == null)
			textColor = Color.BLACK; //default color
		if (textFont == null) 
			textFont = new Font("Serif",Font.PLAIN,12); //default font
	}
	
	public void draw(Graphics g) {
		int modWidth = getDrawnWidth();
		int modHeight = getDrawnHeight();
		g.setColor(backgroundColor);
		g.fillRect(imageX-modWidth/2, imageY-modHeight/2, modWidth, modHeight);
		g.setColor(textColor);
		g.setFont(textFont);
		int strWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, imageX-strWidth/2, imageY+textFont.getSize()/2);
	}
	
	public int getDrawnWidth() {
		Graphics g = parentDisplay.getGraphics();
		g.setFont(textFont);
		int modWidth = (int)(g.getFontMetrics().stringWidth(text)*1.2);
		if (width > -1) //if its defined by the client
			modWidth = width;
		if (modWidth == 0) //if there is no text
			modWidth = textFont.getSize()*2;
		return modWidth;
	}
	
	public int getDrawnHeight() {
		int modHeight = (int)(textFont.getSize()*1.2);
		if (height > -1) //if its defined by the client
			modHeight = height;
		return modHeight;
	}
	
	//some mutator methods to customize the button
	public void setBackgroundColor(Color c) {
		backgroundColor = c;
	}
	
	public void setFont(Font f) {
		textFont = f;
	}
	
	public void setTextColor(Color c) {
		textColor = c;
	}
	
	public void setLocation(int x, int y) {
		this.imageX = x;
		this.imageY = y;
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public String getText() {
		return text;
	}

	public int getImageX() {
		return imageX;
	}

	public int getImageY() {
		return imageY;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setImageX(int imageX) {
		this.imageX = imageX;
	}

	public void setImageY(int imageY) {
		this.imageY = imageY;
	}
}
