package items.weapons;

import items.*;
import entities.*;

public abstract class Weapon extends Item {
	protected double strength = 1; //default
	
	public Weapon() {
		super();
		displayName = "Unnamed Weapon";
	}
	
	public Weapon(Entity owner) {
		super(owner);
	}
}
