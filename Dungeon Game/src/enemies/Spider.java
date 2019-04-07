package enemies;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import entities.Entity;
import entities.PopMessage;

public class Spider extends Enemy {
	public Spider(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.speed = 0.1;
		this.health = this.maxHealth = 5;
	}

	@Override
	public Image getRawImage() {
		BufferedImage img = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		ImageIcon icon = new ImageIcon("lib/assets/images/spoder.png");
		g.drawImage(icon.getImage(), 0, 0, icon.getImage().getWidth(null), icon.getImage().getHeight(null), null);
		return img;
	}

	private boolean trackingPlayer = false, attack = false;
	private int attackCounter = 0;
	@Override
	public void update() {
		if (this.health <= 0)
			this.destroy();
		if (!trackingPlayer) {
			idleAroundHome();
		}
		if (this.distanceFromPlayer() < this.getFollowDistance()) {
			if (!trackingPlayer)
				PopMessage.addPopMessage(new PopMessage("!", getX(), getY(), Color.YELLOW));
			trackingPlayer = true;
			if (this.distanceFromPlayer() < 1.25 && !attack) {
				trackAwayFromPlayer();
			}
			if (this.distanceFromPlayer() > 1.0 || attack)
				trackPlayer();
			if (this.distanceFromPlayer() < 1.25)
				attackCounter++;
			else
				attackCounter = 0;
		} else {
			if (trackingPlayer)
				PopMessage.addPopMessage(new PopMessage("?", getX(), getY(), Color.CYAN));
			trackingPlayer = false;
		}
		
		if (this.intersects(Entity.getPlayer())) {
			Entity.getPlayer().hurt(1.0);
			attack = false;
			attackCounter = 0;
		}
		
		attack = false;
		if (attackCounter >= 20) 
			attack = true;
	}
}
