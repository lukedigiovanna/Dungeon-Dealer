package enemies;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Driver;

import javax.swing.ImageIcon;

import console.Console;
import console.Message;
import display.ImageProcessor;
import entities.*;

public class SampleEnemy extends Enemy {
	public SampleEnemy(double x, double y, double width, double height) {
		super(x,y,width,height);
		setSpeed(0.1);
		setFollowDistance(3);
	}  
	
	@Override
	public Image getRawImage() {
		BufferedImage img = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = img.createGraphics();
		gr.setColor(new Color(0,0,0,0));
		gr.fillRect(0, 0, 32, 32);
		ImageIcon imgIcon = new ImageIcon("lib/assets/images/spoder.png");
		gr.drawImage(imgIcon.getImage(),0,0,32,32,null);
		img = ImageProcessor.scaleToColor(img,c);
		return img;
	}
	
	private Color ranColor() {
		return new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
	}
	Color[] colorLoop = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.MAGENTA};
//	Color[] colorLoop = {Color.RED,Color.ORANGE};
	int index = (int)(Math.random()*colorLoop.length);
	Color c;
	double timer = Math.random();
	double timInc = 0.05;
	boolean trackingPlayer = false;
	@Override
	public void update() {
		timer+=timInc;
		if (timer > 1) {
			timer = 0;
			index++;
			if (index > colorLoop.length-1)
				index = 0;
		}
		int nextColorIndex = index+1;
		if (nextColorIndex > colorLoop.length-1)
			nextColorIndex = 0;
		Color maxC = colorLoop[nextColorIndex];
		Color minC = colorLoop[index];
		int redDis = maxC.getRed()-minC.getRed();
		int greenDis = maxC.getGreen()-minC.getGreen();
		int blueDis = maxC.getBlue()-minC.getBlue();
		int rc = (int)(redDis*timer);
		int gc = (int)(greenDis*timer);
		int bc = (int)(blueDis*timer);
		c = new Color(minC.getRed()+rc,minC.getGreen()+gc,minC.getBlue()+bc);
		
		if (distanceFromPlayer() < getFollowDistance()) {
			trackPlayer();
			if (!trackingPlayer) {
				PopMessage.addPopMessage(new PopMessage("!",getX()+getWidth()/2,getY(),Color.YELLOW));
				//Console.log("An enemy has spotted you!",Color.YELLOW,1000);
			}
			trackingPlayer = true;
		} else {
			idleAroundHome();
			if (trackingPlayer)
				PopMessage.addPopMessage(new PopMessage("?",getX()+getWidth()/2,getY(),Color.CYAN));
			trackingPlayer = false;
		}
		if (intersects(getPlayer())) {
			getPlayer().hurt(5);
			//getPlayer().addGold(1);
			this.destroy();
		}
	}
}
