package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import console.Console;
import display.ImageProcessor;

public class PopMessage {
	private static ArrayList<PopMessage> msgs = new ArrayList<PopMessage>();
	
	private String text = null;
	private Image image = null;
	private double x, y;
	private Color color = Color.WHITE;
	private int lifeSpan = 1000;
	
	public PopMessage(Image img, double x, double y) {
		this(x,y);
		this.image = img;
	}
	
	public PopMessage(double x, double y) {
		this.x = x;
		this.y = y;
		Thread update = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(50);
						update();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		update.start();
	}
	
	public PopMessage(String text, double x, double y) {
		this(text,x,y,Color.WHITE);
	}
	
	public PopMessage(String text, double x, double y, Color color) {
		this(x,y);
		this.text = text;
		this.color = color;
	}
	
	public static void addPopMessage(PopMessage msg) {
		msgs.add(msg);
	}
	
	public static void removePopMessage(PopMessage msg) {
		msgs.remove(msg);
	}
	
	public static ArrayList<PopMessage> getMessages() {
		ArrayList<PopMessage> newMsgs = new ArrayList<PopMessage>();
		for (int i = 0; i < msgs.size(); i++)
			if (msgs.get(i) != null) 
				newMsgs.add(msgs.get(i));
		msgs = newMsgs;
		return newMsgs;
	}
	
	int life = 0;
	double totalUpwardMovement = 3;
	double dx = Math.random()*0.1-0.05;
	double dy = (50.0/lifeSpan)*totalUpwardMovement*-1;
	double ay = 0.02;
	public void update() {
		life+=50;
		if (life >= lifeSpan) {
			int i = msgs.indexOf(this);
			if (i > -1)
				msgs.set(i, null);
			msgs.remove(this);
		}
		y+=dy;
		x+=dx;
		dy+=ay;
		color = new Color(color.getRed(),color.getGreen(),color.getBlue(),(int)(color.getAlpha()*0.9));
		
		if (image != null) {
			double transparency = 1-((double)life/lifeSpan);
			BufferedImage newImage = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
			Graphics g = newImage.getGraphics();
			g.drawImage(image, 0, 0, newImage.getWidth(), newImage.getHeight(), null);
			image = ImageProcessor.transparent(newImage, transparency);
		}
	}
	
	public BufferedImage getImage() {
		BufferedImage img = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		if (text != null) {
			int width = g.getFontMetrics(getFont()).stringWidth(getText());
			img = new BufferedImage(width+10,32,BufferedImage.TYPE_INT_ARGB);
			g = img.getGraphics();
			draw(img.getWidth()/2,img.getHeight()/2,g);
		} else if (image != null){
			img = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
			g = img.getGraphics();
			g.drawImage(image, 0, 0, img.getWidth(),img.getHeight(),null);
		}
		return img;
	}
	
	public String getText() {
		return this.text;
	}
	
	public Font getFont() {
		return new Font("Serif",Font.PLAIN,20);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void draw(int centerX, int centerY, Graphics g) {
		g.setColor(color);
		g.setFont(getFont());
		g.drawString(text, centerX-g.getFontMetrics().stringWidth(text)/2, centerY);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public PopMessage createCopy() {
		double nx = this.x;
		double ny = this.y;
		if (image != null) {
			return new PopMessage(image,nx,ny);
		} else {
			return new PopMessage(text,nx,ny,color);
		}
	}
}
