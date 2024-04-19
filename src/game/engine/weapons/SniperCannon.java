package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class SniperCannon extends Weapon
{
	public static final int WEAPON_CODE = 2;

	public SniperCannon(int baseDamage)
	{
		super(baseDamage);
	}
	public int turnAttack (PriorityQueue<Titan> laneTitans) {
		int resources = 0;
		Titan temp;
		while(true) {
		    temp = laneTitans.peek();
			if(temp == null) {
				break;
			}
			resources = this.attack(temp);
		}
		
		return resources;
	}
}
