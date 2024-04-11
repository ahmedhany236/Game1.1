package game.engine.lanes;

import java.util.*;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.titans.ColossalTitan;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Lane>
{
	private final Wall laneWall;
	private int dangerLevel;
	private final PriorityQueue<Titan> titans;
	private final ArrayList<Weapon> weapons;

	public Lane(Wall laneWall)
	{
		super();
		this.laneWall = laneWall;
		this.dangerLevel = 0;
		this.titans = new PriorityQueue<>();
		this.weapons = new ArrayList<>();
	}

	public Wall getLaneWall()
	{
		return this.laneWall;
	}

	public int getDangerLevel()
	{
		return this.dangerLevel;
	}

	public void setDangerLevel(int dangerLevel)
	{
		this.dangerLevel = dangerLevel;
	}

	public PriorityQueue<Titan> getTitans()
	{
		return this.titans;
	}

	public ArrayList<Weapon> getWeapons()
	{
		return this.weapons;
	}

	@Override
	public int compareTo(Lane o)
	{
		return this.dangerLevel - o.dangerLevel;
	}
	
	public void addTitan(Titan titan) {
		this.titans.add(titan);
	}
	
	public void addWeapon(Weapon weapon) {
		this.weapons.add(weapon);
	}
	
	public void moveLaneTitans() {
		PriorityQueue<Titan> pq = new PriorityQueue<>();
		while (!this.titans.isEmpty()) {
			Titan peekTitan = this.titans.peek();
			peekTitan.setDistance(peekTitan.getDistance() - peekTitan.getSpeed());
			if(peekTitan instanceof ColossalTitan )
				peekTitan.setSpeed(peekTitan.getSpeed()+1);
			pq.add(titans.remove());
		}
		while (!pq.isEmpty()) {
			titans.add(pq.remove());
		}
		
	}
	public int performLaneTitansAttacks() {
		
	}
	public int performLaneWeaponAttacks() {
		
	}
	public boolean isLaneLost() {
		if (this.laneWall.getCurrentHealth()== 0)
				return true;
		else 
			return false;
		
	}
	public void updateLaneDangerLevel() {
		
	}
}
