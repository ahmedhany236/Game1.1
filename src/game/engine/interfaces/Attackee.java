package game.engine.interfaces;

public interface Attackee
{
	int getCurrentHealth();

	void setCurrentHealth(int health);

	int getResourcesValue();
	
	default boolean isDefeated() {
		if (this.getCurrentHealth() <= 0) {
			return true;
		}
		else
			return false;
	}
	
	default int takeDamage(int damage) {
		this.setCurrentHealth(this.getCurrentHealth()- damage);
		if(this.isDefeated()) {
			return this.getResourcesValue();
		}
		else {
			return 0;
		}
	}
	
	

}
