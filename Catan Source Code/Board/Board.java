package Board;
import java.util.*;
import Trading.Player;
import Board.Location.lairOrShip;
import CocoCards.CocoCard;
import CocoCards.CocoDeck;

/**
 * Class for Board in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public class Board {
	
	//===========================================================
	// Class Variables 
	//===========================================================
    public enum corner {
        NE, SE, SW, NW, Regular	
    }
    static Board instance = null;
    public static ArrayList<Island> islands = new ArrayList<Island>();
    public static ArrayList<Location> availableLocations = new ArrayList<Location>();	
    public static ArrayList<DevelopedLocation> developedLocations = new ArrayList<DevelopedLocation>();
    public static CocoDeck cocoDeck = new CocoDeck();
    
	//===========================================================
	// Constructor - Singleton
	//===========================================================
	/**
	 * Constructor for a Board object
	 */
    
    private Board() {} 
    
    // There should only be one instance of Board
    static public Board getInstance() {
		if(instance == null) {
			instance = new Board();
		}
		return instance;
	}
    
    //===========================================================
  	// Getters and Setters
  	//===========================================================
    public ArrayList<Island> getIslands(){
    	return islands;
    }
    
    public CocoDeck getCocoDeck(){
    	return cocoDeck;
    }
    
  	//===========================================================
  	// Other Methods
  	//===========================================================
    
    public void addIsland(int[] center, Board.corner loc, Trading.ResourceHolder.ResourceType resource, int die) {
    	Island island = new Island(center,loc,resource,die);
    	islands.add(island);
    }
    
    public void declareIslands() {
		addIsland(new int[]{ 5,2}, Board.corner.SW,Trading.ResourceHolder.ResourceType.cutlass,4);
		addIsland(new int[]{9,2}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.wood,1);
		addIsland(new int[]{ 13,2}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.goat,2);
		addIsland(new int[]{ 17,2}, Board.corner.SE,Trading.ResourceHolder.ResourceType.molasses,4);

		addIsland(new int[]{ 3,4}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.wood,3);
		addIsland(new int[]{ 7,4}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.gold,5);
		addIsland(new int[]{ 11,4}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.none,6);
		addIsland(new int[]{ 15,4}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.gold,3);
		addIsland(new int[]{ 19,4}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.goat,5);

		addIsland(new int[]{ 5,6}, Board.corner.NW,Trading.ResourceHolder.ResourceType.cutlass,1);
		addIsland(new int[]{ 9,6}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.wood,2);
		addIsland(new int[]{ 13,6}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.goat,1);
		addIsland(new int[]{ 17,6}, Board.corner.NE,Trading.ResourceHolder.ResourceType.molasses,2);
    }
    
    public boolean addToBoard(Location location) {
    	for(int i=0; i < availableLocations.size();i++) {
    		if(availableLocations.get(i).isEqual(location.getX(),location.getY()))
    			return false;
    	}
    	availableLocations.add(location);
    	return true;
    }
    
    public void printEach() {
    	System.out.println("\nThe Locations on board:");
    	for(int i=0; i < availableLocations.size();i++) {
    		System.out.println(availableLocations.get(i).toString()+", i="+(i+1));    		
    	}
    }
       
    public int PositionAvailable(int xi, int yi) {
    	for(int i=0; i < availableLocations.size();i++) {
	       	if(availableLocations.get(i).isEqual(xi, yi)) {
	   			return i;
	   		}
	   	}
		return -1;
	 }
    
    public int PositionDeveloped(int xi, int yi) {
    	for(int i=0; i < developedLocations.size();i++) {
    		if(developedLocations.get(i).isEqual(xi, yi)) {
	   			return i;
	   		}
	   	}
		return -1;
	}

    // Buys a ship or lair for player p if they have the required resources
    public boolean buyLairOrShip(int xi,int yi, Player p) {
    	int i = this.PositionAvailable(xi,yi);
    	lairOrShip lairOrShip = availableLocations.get(i).getLairOrShip();
    	if(canBuildOnLocation(xi,yi,p)) {
	    	if(lairOrShip == lairOrShip.lair && p.buildLair()) {
	    		this.developLocation(xi,yi,p);
	    		return true;
	    	}
	    	else if(lairOrShip == lairOrShip.ship && p.buildShip()) {
	    		this.developLocation(xi,yi,p);
	    		return true;
	    	}
	    	else
	    		return false; 
    	}
    	else
    		return false;
    }
    // Used to check if the player is adjacent to this position and if it is undeveloped
    private boolean canBuildOnLocation (int xi, int yi, Player p){
    	int i = this.PositionAvailable(xi,yi);
    	boolean  positionAvailableToPlayer = p.positionAvailableForPlayer(xi, yi);
    	if(i!=-1 && positionAvailableToPlayer) {
    		int x = availableLocations.get(i).getX();
    		int y = availableLocations.get(i).getY();
    		return true;
    	}
    	else if(i==-1) {
    		System.out.println("This Position is already occupied.");
    		return false;
    	}
    	else if(!positionAvailableToPlayer) {
    		System.out.println("This position is not adjacent to any of this player's developed locations.");
    		return false;
    	}
    	else {
    		System.out.println("This position is occupied and is not adjacent to any of"
    				+ " this player's positions.");
    		return false;
    	}
    }
    // Used to develop a position on the board
    public void developLocation(int xi,int yi, Player p) {
    	if(canBuildOnLocation(xi,yi,p)) {
	    	int i = this.PositionAvailable(xi,yi);
			int x = availableLocations.get(i).getX();
			int y = availableLocations.get(i).getY();
			lairOrShip lairOrShip = availableLocations.get(i).getLairOrShip();
			DevelopedLocation DL = new DevelopedLocation(x,y,lairOrShip,p);
			developedLocations.add(DL);
			p.DevelopedPlayerLocations.add(DL);
			availableLocations.remove(i);
	
			for(int j = 0; j < islands.size(); j++) {
				islands.get(j).developPosition(xi, yi, p);
			}
    	}
    }
    
    public void handleGeneratingIslandResources(int result, Player player) {
    		for (Island island: islands) {
    			if(result == island.die) {
    				island.generateResources();
    			}
    		}
    }
 
    public void printDevelopedLocations() {
    	for(DevelopedLocation D : Board.developedLocations) {
    		System.out.println(D.getX()+","+D.getY());
    	}
    }
    
    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    
    public void destroyMe() {
        instance = null;
    }
}