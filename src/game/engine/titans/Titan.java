package game.engine.titans;

import game.engine.interfaces.*;

public abstract class Titan implements Comparable<Titan> ,Attackee,Attacker,Mobil{
	private final int baseHealth;
	private int currentHealth ; //r,w
	private final int baseDamage;
	private final int heightInMeters;
	private int distanceFromBase; //r,w
	private int speed; //r,w
	private final int resourcesValue;
	private final int dangerLevel;
	public Titan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase,
				int speed, int resourcesValue, int dangerLevel) {
		this.currentHealth = baseHealth;
		this.baseHealth = baseHealth;
		this.baseDamage=baseDamage;
		this.heightInMeters=heightInMeters;
		this.distanceFromBase=distanceFromBase;
		this.speed=speed;
		this.resourcesValue=resourcesValue;
		this.dangerLevel=dangerLevel;
	}
	public int getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(int currentHealth) {
		if (currentHealth<0)
			this.currentHealth = 0;
		else
		this.currentHealth = currentHealth;
	}
	public int getDistance() {
		return distanceFromBase;
	}
	public void setDistance(int distanceFromBase) {
		if (distanceFromBase < 0)
			this.distanceFromBase = 0;
		else
			this.distanceFromBase = distanceFromBase;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		if (speed < 0)
			this.speed =0;
		else
			this.speed = speed;
	}
	public int getBaseHealth() {
		return baseHealth;
	}
	public int getDamage() {
		return baseDamage;
	}
	public int getHeightInMeters() {
		return heightInMeters;
	}
	public int getResourcesValue() {
		return resourcesValue;
	}
	public int getDangerLevel() {
		return dangerLevel;
	}
	public int compareTo(Titan o) {
		return this.distanceFromBase - ((Titan)o).distanceFromBase;
	}
	
}



