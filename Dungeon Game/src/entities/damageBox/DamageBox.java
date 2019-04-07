package entities.damageBox;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import entities.*;

public class DamageBox extends Entity {
	private Entity owner;
	private int lifeSpan = 1000;
	private double strength = 1.0;
	private boolean destroyOnHit = false;
	
	public DamageBox(double x, double y) {
		this(x,y,null);
	}
	
	public DamageBox(double x, double y, Entity owner) {
		this(x,y,1,1,owner);
	}
	
	public DamageBox(double x, double y, double width, double height, Entity owner) {
		super(x, y, width, height);
		this.owner = owner;
		this.setCurrentLevel(owner.getCurrentLevel());
	}
	
	public DamageBox destroyOnHit() {
		destroyOnHit = true;
		return this;
	}
	
	public DamageBox power(double power) {
		strength = power;
		return this;
	}

	@Override
	public Image getRawImage() {
		BufferedImage img = new BufferedImage((int)(getWidth()*32),(int)(getHeight()*32),BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		//THIS IS TEMPORARY!!!! ACTUAL DAMAGEBOXES WILL NOT HAVE ANY IMAGE
		ImageIcon ico = new ImageIcon("lib/assets/images/fireball.png");
		g.drawImage(ico.getImage(),0, 0, img.getWidth(),img.getHeight(),null);
		return img;
	}

	@Override
	public void update() {
		if (intersectsWall() != null && age < 150) {
			hasCollision = false;
			canMove = false;
		}
		
		if (this.age >= this.lifeSpan)
			this.destroy();
		
		for (int i = 0; i < Entity.entities.size(); i++) {
			Entity e = Entity.entities.get(i);
			if (e instanceof DamageBox)
				continue;
			if (this.owner != e && !(e instanceof LightSource)) {
				if (this.intersects(e)) {
					e.hurt(strength);
					if (this.destroyOnHit)
						this.destroy();
				}
			}
		}
		
		this.move(xv, yv);
	}
	
	public void setLifeSpan(int ls) {
		this.lifeSpan = ls;
	}
}
