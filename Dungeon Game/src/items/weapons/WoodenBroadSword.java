package items.weapons;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import entities.*;
import entities.damageBox.*;

public class WoodenBroadSword extends Weapon {
	public WoodenBroadSword() {
		this(null);
	}
	
	public WoodenBroadSword(Entity owner) {
		super(owner);
		displayName = "Wooden Broad Sword";
		description = "Deals 5 damage in the direction you swing";
		durability = maxDurability = 50;
		strength = 5;
	}
	
	public Image getImage() {
		BufferedImage img = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		ImageIcon ico = new ImageIcon("lib/assets/images/wooden_sword.png");
		g.drawImage(ico.getImage(),0,0,32,32,null);
		return img;
	}
	
	public void use() {
		if (owner != null) {
			double width = 0.4, height = 0.4;
			//for (double i = 0; i < 360; i+=(360/50)) {
				DamageBox dmgBox = new DamageBox(owner.getX()+owner.getWidth()/2-width/2,owner.getY()+owner.getHeight()/2-height/2,owner)
						.power(2.5)
						.destroyOnHit()
				;
				dmgBox.setWidth(width);
				dmgBox.setHeight(height);
				double angle = 0;
				switch (owner.orientation) {
				case NORTH:
					angle = 90;
					break;
				case WEST:
					angle = 180;
					break;
				case SOUTH:
					angle = 270;
					break;
				default:
					break;
				}
				dmgBox.setVelocity(angle,0.3);
				dmgBox.setLifeSpan(3000);
				dmgBox.addLightSource(new LightSource(1));
				Entity.entities.add(dmgBox);
			//}
		}
		durability--;
	}
}
