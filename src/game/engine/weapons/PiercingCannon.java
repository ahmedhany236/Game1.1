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
		Titan temp;
		PriorityQueue<Titan> tempqueue = new PriorityQueue<Titan>();
		while(true) {
		    temp = laneTitans.remove();
			for (int i = 0 ; i<5;i++) {
			if(temp == null) {
				break;
			}
			resources = this.attack((Attackee) temp);
			tempqueue.add(temp);
			}
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
