package enemies;

import java.awt.*;

import entities.*;

public abstract class Enemy extends Entity {
	//instance variables
	//the homeX is the position on the grid that the enemy will spawn and wont stray far from
	private double homeX, homeY;
	private double tetheredDistance = 3.0, followDistance = 3.0; //default
	
	public Enemy(double x, double y, double width, double height) {
		super(x,y,width,height);
		type = Type.ENEMY;
		this.homeX = x;
		this.homeY = y;
	}
	
	public double distanceFromHome() {
		double dx = this.getCenterX()-this.homeX;
		double dy = this.getCenterY()-this.homeY;
		double distance = Math.sqrt(dx*dx+dy*dy); //use pythagorean theorem to find the distance
		return distance;
	}
	
	public double distanceFromPlayer() {
		if (Entity.getPlayer() == null)
			return 10000;
		double dx = this.getCenterX()-Entity.getPlayer().getCenterX();
		double dy = this.getCenterY()-Entity.getPlayer().getCenterY();
		double distance = Math.sqrt(dx*dx+dy*dy);
		return distance;
	}
	
	//the enemy will occasionally walk around
	private double targetX = getHomeX(), targetY = getHomeY();
	public void idleAroundHome() {
		if (Math.random() < 0.01) { //low chance but is called several times a second -- averages once every 5 seconds
			targetX = this.getHomeX()+(Math.random()*this.getTetheredDistance()*2)-this.getTetheredDistance();
			targetY = this.getHomeY()+(Math.random()*this.getTetheredDistance()*2)-this.getTetheredDistance();
		}
		//travel to the target -- use trig to get separate x and y components
		double distanceX = targetX-getCenterX();
		double distanceY = targetY-getCenterY();
		double distance = Math.sqrt(distanceX*distanceX+distanceY*distanceY);
		//hypotenuse of the velocity triangle is the speed
		double dx = (distanceX/distance)*getSpeed();
		if (doubleEquals(distanceX,0))
				dx = 0;
		double dy = (distanceY/distance)*getSpeed();
		if (distance < getSpeed()*2) 
			dx=dy=0;
		move(dx,dy);
	}
	
	public void trackPlayer() {
		if (Entity.getPlayer() == null)
			return;
		double distanceX = Entity.getPlayer().getCenterX()-getCenterX();
		double distanceY = Entity.getPlayer().getCenterY()-getCenterY();
		double distance = Math.sqrt(distanceX*distanceX+distanceY*distanceY); //pythagorean theorem
		double dx = (distanceX/distance)*getSpeed(); //uses trigonometry here without using the Math functions
		double dy = (distanceY/distance)*getSpeed();
		if (distance < .1)
			dx=dy=0;
		move(dx,dy);
	}
	
	public void trackAwayFromPlayer() {
		if (Entity.getPlayer() == null)
			return;
		double distanceX = Entity.getPlayer().getCenterX()-getCenterX();
		double distanceY = Entity.getPlayer().getCenterY()-getCenterY();
		double distance = Math.sqrt(distanceX*distanceX+distanceY*distanceY); //pythagorean theorem
		double dx = (distanceX/distance)*getSpeed(); //uses trigonometry here without using the Math functions
		double dy = (distanceY/distance)*getSpeed();
		if (distance < .1)
			dx=dy=0;
		move(-dx,-dy);
	}
	
	//inherited from superclass
	public abstract Image getRawImage();
	
	//inherited from superclass but only used as a placeholder for organization
	public abstract void update();

	public double getTetheredDistance() {
		return tetheredDistance;
	}

	public void setTetheredDistance(double tetheredDistance) {
		this.tetheredDistance = tetheredDistance;
	}

	public double getFollowDistance() {
		return followDistance;
	}

	public void setFollowDistance(double followDistance) {
		this.followDistance = followDistance;
	}

	public void setHomeX(double homeX) {
		this.homeX = homeX;
	}

	public void setHomeY(double homeY) {
		this.homeY = homeY;
	}

	public void setHome(double x, double y) {
		this.homeX = x;
		this.homeY = y;
	}
	
	public double getHomeX() {
		return this.homeX;
	}
	
	public double getHomeY() {
		return this.homeY;
	}
}
