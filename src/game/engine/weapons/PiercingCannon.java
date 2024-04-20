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
		for (int i=0;i<5;i++) {
			temp = laneTitans.remove();
			if(temp == null || temp.isDefeated())
				break;
			resources += this.attack(temp);
			tempqueue.add(temp);
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
