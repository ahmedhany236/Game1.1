package game.engine.weapons;

import java.util.AbstractQueue;
import java.util.PriorityQueue;

import game.engine.interfaces.Attackee;
import game.engine.titans.Titan;

public class PiercingCannon extends Weapon
{
	public static final int WEAPON_CODE = 1;

	public PiercingCannon(int baseDamage)
	{
		super(baseDamage);
	}
	public int turnAttack (PriorityQueue<Titan> laneTitans) {
		int resources = 0;
		for (int i = 0 ; i<5;i++) {
			attack((Attackee) laneTitans);
			if (((Attackee) laneTitans).isDefeated()) {
				resources += ((Attackee) laneTitans).getResourcesValue();
				laneTitans.remove();
			}
		}
		return resources;
	}

}
