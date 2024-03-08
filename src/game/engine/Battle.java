package game.engine;
import java.io.IOException;
import java.util.*;

import game.engine.base.Wall;
import game.engine.weapons.factory.*;
import game.engine.titans.*;
import game.engine.dataloader.DataLoader;
import game.engine.lanes.Lane;
public class Battle {
	private final static int[][] PHASES_APPROACHING_TITANS = {
	        {1,1,1,2,1,3,4},{2,2,2,1,3,3,4},{4,4,4,4,4,4,4} 
	};
						
	private final static int WALL_BASE_HEALTH = 10000;
	private int numberOfTurns; 
	private int resourcesGathered;
	private BattlePhase battlePhase; 
	private int numberOfTitansPerTurn; 
	private int score; 
	private int titanSpawnDistance;
	private final WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives;
	private final ArrayList<Titan> approachingTitans;
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes;
	
	
	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
			int initialResourcesPerLane) throws IOException{
		this.numberOfTurns = numberOfTurns;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.resourcesGathered = initialNumOfLanes *initialResourcesPerLane;
		this.approachingTitans = new ArrayList<Titan>();
		this.lanes =new PriorityQueue<Lane>(); //possible error in order
		this.originalLanes = new ArrayList<Lane>();
		this.weaponFactory = new WeaponFactory();
		initializeLanes(initialNumOfLanes);	
		this.titansArchives = DataLoader.readTitanRegistry();
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		
	}
	
	private void initializeLanes(int numOfLanes) {
		for (int i = 0; i<numOfLanes ; i++) {
			Lane l = new Lane(new Wall(WALL_BASE_HEALTH));
			originalLanes.add(l);
			lanes.add(l); //possible error in order
		}
	}
	public static int getWALL_BASE_HEALTH() {
		return WALL_BASE_HEALTH;
	}
	public PriorityQueue<Lane> getLanes() {
		return lanes;
	}
	public WeaponFactory getWeaponFactory() {
		return weaponFactory;
	}
	public int getNumberOfTurns() {
		return numberOfTurns;
	}
	public void setNumberOfTurns(int numberOfTurns) {
		if (numberOfTurns < 0)
			this.numberOfTurns = 0;
		else
			this.numberOfTurns = numberOfTurns;
	}
	public int getResourcesGathered() {
		return resourcesGathered;
	}
	public void setResourcesGathered(int resourcesGathered) {
		this.resourcesGathered = resourcesGathered;
	}
	public BattlePhase getBattlePhase() {
		return battlePhase;
	}
	public void setBattlePhase(BattlePhase battlePhase) {
		this.battlePhase = battlePhase;
	}
	public int getNumberOfTitansPerTurn() {
		return numberOfTitansPerTurn;
	}
	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
		if (numberOfTitansPerTurn < 0)
			this.numberOfTitansPerTurn = 0;
		else
			this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		if (score < 0)
			this.score =0;
		else
			this.score = score;
	}
	public int getTitanSpawnDistance() {
		return titanSpawnDistance;
	}
	public void setTitanSpawnDistance(int titanSpawnDistance) {
		if (titanSpawnDistance < 0)
			this.titanSpawnDistance =0;
		else
			this.titanSpawnDistance = titanSpawnDistance;
	}
	public ArrayList<Titan> getApproachingTitans() {
		return approachingTitans;
	}
	public HashMap<Integer, TitanRegistry> getTitansArchives() {
		return titansArchives;
	}
	public static int[][] getPhasesApproachingTitans() {
		return PHASES_APPROACHING_TITANS;
	}
	public ArrayList<Lane> getOriginalLanes() {
		return originalLanes;
	}
}
