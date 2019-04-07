package entities.players;

//an overarching class that contains data for the player
/*
 * contains:
 * health
 * mana
 * strength
 * resistance
 * luck
 * armor points
 * inventory
 * any other stuff i add later
 */
public class PlayerAttribs {
	public double mana, maxMana, strength, inherentResistance, luck, armorPoints;
	public int gold;
	public Inventory inventory;
}
