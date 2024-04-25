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
		PriorityQueue<Titan> pq = new PriorityQueue<Titan>();
		if (laneTitans.isEmpty())
			return 0;
		if(laneTitans.size()>=5) {
			for (int i=0;i<5;i++) {
				temp = laneTitans.remove();
				resources += this.attack(temp);
				pq.add(temp);
				}	
		}
		else {
			while (!laneTitans.isEmpty()) {
				temp = laneTitans.remove();
				resources += this.attack(temp);
				pq.add(temp);
			}
		}
			while(!pq.isEmpty()) {
				if (pq.peek().isDefeated()) 
					pq.remove();
				else 
					laneTitans.add(pq.remove());
			}
		return resources;
	}
}
