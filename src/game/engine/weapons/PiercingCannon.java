package game.engine.weapons;

import java.util.PriorityQueue;
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
		Titan temp;
		PriorityQueue<Titan> tempqueue = new PriorityQueue<Titan>();
		if (laneTitans.isEmpty())
			return 0;
		if(laneTitans.size()>5) {
			for (int i=0;i<5;i++) {
				temp = laneTitans.remove();
				resources += this.attack(temp);
				tempqueue.add(temp);
				}	
			}
		else {
			while (!laneTitans.isEmpty()) {
				temp = laneTitans.remove();
				resources += this.attack(temp);
				tempqueue.add(temp);
			}
		}
			while(!tempqueue.isEmpty()) {
				if (tempqueue.peek().isDefeated()) 
					tempqueue.remove();
				else 
					laneTitans.add(tempqueue.remove());
			}
		return resources;
	}
}
