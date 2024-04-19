package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.interfaces.Attackee;
import game.engine.titans.Titan;

public class VolleySpreadCannon extends Weapon
{
	public static final int WEAPON_CODE = 3;

	private final int minRange;
	private final int maxRange;

	public VolleySpreadCannon(int baseDamage, int minRange, int maxRange)
	{
		super(baseDamage);
		this.minRange = minRange;
		this.maxRange = maxRange;
	}

	public int getMinRange()
	{
		return minRange;
	}

	public int getMaxRange()
	{
		return maxRange;
	}
	public int turnAttack (PriorityQueue<Titan> laneTitans) {
		int resources = 0;
		Titan temp;
		PriorityQueue<Titan> tempqueue = new PriorityQueue<Titan>();
		while(true) {
			temp = laneTitans.remove();
			if(temp == null) {
				break;
			}
			if (temp.getDistance()<this.getMaxRange() && temp.getDistance()>this.getMinRange()) {
				resources = this.attack((Attackee) temp);
			}
				tempqueue.add(temp);
		}
		while(tempqueue.peek()!= null) {
				if (tempqueue.peek().isDefeated()) {
					tempqueue.remove();
				}
				else {
					laneTitans.add(tempqueue.peek());
				}
			}
			return resources;
	
	}

}
