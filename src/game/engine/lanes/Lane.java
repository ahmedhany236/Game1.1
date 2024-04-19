package game.engine.lanes;

import java.util.*;
import game.engine.base.Wall;
import game.engine.titans.*;
import game.engine.weapons.*;

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
		PriorityQueue<Titan> pq = new PriorityQueue<Titan>();
		while (!this.titans.isEmpty()) {
			Titan peekTitan = this.titans.remove();
			if (peekTitan.hasReachedTarget()){
				peekTitan.move();
			}
			pq.add(peekTitan);
		}
		while (!pq.isEmpty()) {
			titans.add(pq.remove());
		}
		
	}
	public int performLaneTitansAttacks() {
		PriorityQueue<Titan> pq = new PriorityQueue<Titan>();
		int resources = 0;
		while (!this.titans.isEmpty()) {
			Titan peekTitan = this.titans.remove();
			resources += peekTitan.attack(this.getLaneWall());
			pq.add(peekTitan);
		}
		while (!pq.isEmpty()) {
			titans.add(pq.remove());
		}
		return resources;
	}
	public int performLaneWeaponAttacks() {
		PriorityQueue<Titan> pq = new PriorityQueue<Titan>();
		int resources = 0;
		while (!this.titans.isEmpty()) {
			Titan temp = this.getTitans().remove();
			for(int i =0;i<this.getWeapons().size();i++) {
				resources +=this.getWeapons().get(i).attack(temp);
			}
			pq.add(temp);
		}
		while (!pq.isEmpty()) {
			titans.add(pq.remove());
		}
		return resources;
	}
	public boolean isLaneLost() {
		return this.laneWall.isDefeated();
	}
	public void updateLaneDangerLevel() {
		PriorityQueue<Titan> pq = new PriorityQueue<Titan>();
		Titan peekTitan = this.titans.remove();
		int updated = 0;
		while (!this.titans.isEmpty()) {
			updated+= peekTitan.getDangerLevel();
		}
		while (!pq.isEmpty()) {
			titans.add(pq.remove());
		}
		this.setDangerLevel(updated);
		
	}
}
