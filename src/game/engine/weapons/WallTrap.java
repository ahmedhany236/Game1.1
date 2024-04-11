package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.interfaces.Attackee;
import game.engine.titans.Titan;

public class WallTrap extends Weapon
{
	public static final int WEAPON_CODE = 4;

	public WallTrap(int baseDamage)
	{
		super(baseDamage);
	}
	
	public int turnAttack (PriorityQueue<Titan> laneTitans) {
		int resources = 0;
		Titan temp;
		temp = laneTitans.remove();
		if(temp == null) {
				break;
		}
		if(laneTitans.peek().getDistance()==0) {
			resources = this.attack((Attackee) temp);
	}
	}
}
