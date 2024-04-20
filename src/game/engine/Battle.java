package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.factory.WeaponFactory;

public class Battle
{
	private static final int[][] PHASES_APPROACHING_TITANS =
	{
		{ 1, 1, 1, 2, 1, 3, 4 },
		{ 2, 2, 2, 1, 3, 3, 4 },
		{ 4, 4, 4, 4, 4, 4, 4 } 
	}; // order of the types of titans (codes) during each phase
	private static final int WALL_BASE_HEALTH = 10000;

	private int numberOfTurns;
	private int resourcesGathered;
	private BattlePhase battlePhase;
	private int numberOfTitansPerTurn; // initially equals to 1
	private int score; // Number of Enemies Killed
	private int titanSpawnDistance;
	private final WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives;
	private final ArrayList<Titan> approachingTitans; // treated as a Queue
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes;

	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
			int initialResourcesPerLane) throws IOException
	{
		super();
		this.numberOfTurns = numberOfTurns;
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.resourcesGathered = initialResourcesPerLane * initialNumOfLanes;
		this.weaponFactory = new WeaponFactory();
		this.titansArchives = DataLoader.readTitanRegistry();
		this.approachingTitans = new ArrayList<Titan>();
		this.lanes = new PriorityQueue<>();
		this.originalLanes = new ArrayList<>();
		this.initializeLanes(initialNumOfLanes);
	}

	public int getNumberOfTurns()
	{
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns)
	{
		this.numberOfTurns = numberOfTurns;
	}

	public int getResourcesGathered()
	{
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered)
	{
		this.resourcesGathered = resourcesGathered;
	}

	public BattlePhase getBattlePhase()
	{
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase)
	{
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn()
	{
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn)
	{
		this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public int getTitanSpawnDistance()
	{
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance)
	{
		this.titanSpawnDistance = titanSpawnDistance;
	}

	public WeaponFactory getWeaponFactory()
	{
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives()
	{
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans()
	{
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes()
	{
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes()
	{
		return originalLanes;
	}

	private void initializeLanes(int numOfLanes)
	{
		for (int i = 0; i < numOfLanes; i++)
		{
			Wall w = new Wall(WALL_BASE_HEALTH);
			Lane l = new Lane(w);

			this.getOriginalLanes().add(l);
			this.getLanes().add(l);
		}
	}
	public void refillApproachingTitans() {
		switch (this.getBattlePhase()) {
		case EARLY: 
			for(int i = 0;i<7; i++) {
				titansArchives.get(PHASES_APPROACHING_TITANS[1][i]).spawnTitan(titanSpawnDistance);
			}
		case INTENSE:
			for(int i = 0;i<7; i++) {
				titansArchives.get(PHASES_APPROACHING_TITANS[2][i]).spawnTitan(titanSpawnDistance);
			}
		case GRUMBLING:
			for(int i = 0;i<7; i++) {
				titansArchives.get(PHASES_APPROACHING_TITANS[3][i]).spawnTitan(titanSpawnDistance);
			}
		}
	}
	public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException,
	InvalidLaneException{ 
		if(!lane.isLaneLost()) {
			try { 
				lane.addWeapon(weaponFactory.buyWeapon(this.resourcesGathered,weaponCode).getWeapon());
			}
			catch(InvalidLaneException e){
				new InvalidLaneException();
			}
				
			try {
				lane.addWeapon(weaponFactory.buyWeapon(this.resourcesGathered,weaponCode).getWeapon());
			}
			catch (InsufficientResourcesException e) {
				new InsufficientResourcesException(this.resourcesGathered);
			}
			
		}
			
	}
	public void passTurn()
	{
		
	}
	private void addTurnTitansToLane() {
		PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		Lane temp = lanes.peek();
		for(int i = 0;i<lanes.size();i++) {
			if(lanes.peek().isLaneLost()) {
				pq.add(lanes.remove());
			}
			else {
				temp = lanes.peek();
				pq.add(lanes.remove());
			}
		}
		int j = 0;
		for(int i = 0;i< numberOfTitansPerTurn;i++ ) {
			if(approachingTitans.isEmpty()) {
				refillApproachingTitans();
				j = 0;
			}
			temp.addTitan(approachingTitans.remove(j));
			j++;
		}
		pq.add(temp);
		while(!pq.isEmpty()) {
			lanes.add(pq.remove());
		}
		
		
	}
	private void moveTitans() {
		PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		PriorityQueue<Titan> pqtitans = new PriorityQueue<Titan>();
		while (!lanes.isEmpty()) {
			Lane templane= lanes.remove();
			while(!templane.getTitans().isEmpty()) {
				Titan temp = templane.getTitans().remove();
				temp.move();
				pqtitans.add(temp);	
			}
			while(!pqtitans.isEmpty()) {
				templane.getTitans().add(pqtitans.remove());
			}
			pq.add(templane);
		}
		while(!pq.isEmpty()) {
			lanes.add(pq.remove());
		}
		
	}
	private int performWeaponsAttacks() {
		PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		int resources = 0;
		while (!lanes.isEmpty()) {
			Lane temp = lanes.remove();
			if(!temp.isLaneLost()) {
				resources += temp.performLaneWeaponAttacks();
				pq.add(temp);
			}
			
			
		}
		while(!pq.isEmpty()) {
			lanes.add(pq.remove());
		}
		return resources;
		
	}
	
	private int performTitansAttacks() {
		PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		int resources = 0;
		while (!lanes.isEmpty()) {
			Lane temp = lanes.remove();
			resources += temp.performLaneTitansAttacks();
			pq.add(temp);
			
		}
		while(!pq.isEmpty()) {
			lanes.add(pq.remove());
		}
		return resources;
	}
	private void updateLanesDangerLevels() {
		PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		while (!lanes.isEmpty()) {
			if (!lanes.peek().isLaneLost()) {
				Lane temp = lanes.remove();
				temp.updateLaneDangerLevel();
				pq.add(temp);
			}
		}
		while(!pq.isEmpty()) {
			lanes.add(pq.remove());
		}
	}
	private void finalizeTurns() {
		numberOfTurns++;
		if (numberOfTurns<15) {
			battlePhase = BattlePhase.EARLY;
		}
		else
			if(numberOfTurns<30) {
				battlePhase = BattlePhase.INTENSE;
		}
		else {
			if (numberOfTurns>30 && numberOfTurns%5== 0) {
				battlePhase = BattlePhase.GRUMBLING	;
				numberOfTitansPerTurn *= 2;
			}
			else {
				battlePhase = BattlePhase.GRUMBLING	;
			}
		}	
	}
	private void performTurn() {
		moveTitans();
		performWeaponsAttacks();
		performTitansAttacks();
		addTurnTitansToLane();
		updateLanesDangerLevels();
		finalizeTurns();
		
	}
	public boolean isGameOver() {
		boolean flag = true;
		PriorityQueue<Lane> pq = new PriorityQueue<Lane>();
		pq = lanes;
		while(pq.isEmpty()) {
			if(!pq.remove().isLaneLost()) {
				flag = false;
				break;
			}
		}
		return flag;
			
	}
	
	
}

		//hi
	
