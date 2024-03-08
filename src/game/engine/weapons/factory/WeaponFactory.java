package game.engine.weapons.factory;
import java.io.IOException;
import java.util.*;
import game.engine.dataloader.*;
import game.engine.weapons.*;

public class WeaponFactory {
	private final HashMap<Integer, WeaponRegistry> weaponShop;
	
	public WeaponFactory() throws IOException{
		this.weaponShop=DataLoader.readWeaponRegistry();
	}
	public HashMap<Integer, WeaponRegistry> getWeaponShop() {
		return weaponShop;
	}

}

