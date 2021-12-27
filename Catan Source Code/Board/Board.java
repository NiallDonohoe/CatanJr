package Board;
import java.util.*;
import Trading.Player;
import Board.Location.lairOrShip;
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
    private static Board instance = null;
    private static ArrayList<Island> islands = new ArrayList<Island>();
    private static ArrayList<Location> availableLocations = new ArrayList<Location>();	
    private static ArrayList<DevelopedLocation> developedLocations = new ArrayList<DevelopedLocation>();
    private static CocoDeck cocoDeck = new CocoDeck();
    
	//===========================================================
	// Constructor - Singleton
	//===========================================================
    /**
     * getInstance method returns single instance of board.
     * @return theBoard. singleton Board object.
     */
    public static Board getInstance() {
		if(instance == null) {
			instance = new Board();
		}
		return instance;
	}
    
    //===========================================================
  	// Getters and Setters
  	//===========================================================
    /**
     * getIslands method.
     * @return islands. An ArrayList of island objects
     */
    public ArrayList<Island> getIslands(){
    	return islands;
    }
    
    /**
     * getAvailableLocations method.
     * @return availableLocations. An ArrayList of undeveloped locations
     */
    public ArrayList<Location> getAvailableLocations(){
    	return availableLocations;
    }
    
    /**
     * getDevelopedLocations method.
     * @return developedLocations. An ArrayList of locations with either a Lair or Ship built
     */
    public ArrayList<DevelopedLocation> getDevelopedLocations(){
    	return developedLocations;
    }
    
    /**
     * getCocoDeck method.
     * @return cocoDeck. A cocoDeck object.
     */
    public CocoDeck getCocoDeck(){
    	return cocoDeck;
    }
    
    /**
    * getSpookyIsland returns the SpookyIsland on the board.
    */ 
    public SpookyIsland getSpookyIsland() {
    	for(int i = 0; i < islands.size(); i++ ) {
    		if(islands.get(i) instanceof SpookyIsland) {
    			return ((SpookyIsland) islands.get(i));
    		}
    	}
		return null;
    }
    
  	//===========================================================
  	// Methods for Adding Islands to the Board
  	//===========================================================
    /**
     * addIsland adds an island object to the board.
     * @param center is the [x,y] coordinate of the island as defined on the map
     * @param corner is the enum indicating if the island is one of the 4 edge islands or a regular island
     * @param resource is the resource type associated with the island
     * @param die is the die number associated with the island
     */
    private void addIsland(int[] center, Board.corner corner, Trading.ResourceHolder.ResourceType resource, int die) {
    	Island island = new Island(center,corner,resource,die);
    	islands.add(island);
    }
    
    /**
     * addSpookyIsland adds a SpookyIsland object to the board.
     */
    private void addSpookyIsland() {
    	islands.add(SpookyIsland.getInstance());
    }
    
    /**
     * declareIslands adds each island as explicitly defined within the method
     */
    public void declareIslands() {
		addIsland(new int[]{5,2}, Board.corner.SW,Trading.ResourceHolder.ResourceType.cutlass,4);
		addIsland(new int[]{9,2}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.wood,1);
		addIsland(new int[]{13,2}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.goat,2);
		addIsland(new int[]{17,2}, Board.corner.SE,Trading.ResourceHolder.ResourceType.molasses,4);

		addIsland(new int[]{3,4}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.wood,3);
		addIsland(new int[]{7,4}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.gold,5);
		
		addSpookyIsland();
		
		addIsland(new int[]{15,4}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.gold,3);
		addIsland(new int[]{19,4}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.goat,5);

		addIsland(new int[]{5,6}, Board.corner.NW,Trading.ResourceHolder.ResourceType.cutlass,1);
		addIsland(new int[]{9,6}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.wood,2);
		addIsland(new int[]{13,6}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.goat,1);
		addIsland(new int[]{17,6}, Board.corner.NE,Trading.ResourceHolder.ResourceType.molasses,2);
    }
    
  	//===========================================================
  	// Methods for manipulating locations and islands
  	//===========================================================
    /**
     * addToBoard adds a location to the board if the location is valid
     * @param location is the location to be added
     * @return boolean indicating if location added
     */
    protected boolean addToBoard(Location location) {
    	for(Location L: availableLocations) {
    		if(L.isEqual(location.getX(),location.getY())) {
    			return false;
    		}	
    	}
    	availableLocations.add(location);
		return true;
    }
    
    /**
     * positionAvailable checks if a location is available
     * @param xi x coordinate of location
     * @param yi y coordinate of location
     * @return i an int indication where in the ArrayList availableLocations the location is
     */  
    public int positionAvailable(int xi, int yi) {
    	for(int i=0; i < availableLocations.size();i++) {
	       	if(availableLocations.get(i).isEqual(xi, yi)) {
	   			return i;
	   		}
	   	}
		return -1;
	 }
    
    /**
     * positionDeveloped checks if a location is developed
     * @param xi x coordinate of location
     * @param yi y coordinate of location
     * @return i an int indication where in the ArrayList developedLocations the location is
     */ 
    protected int positionDeveloped(int xi, int yi) {
    	for(int i=0; i < developedLocations.size();i++) {
    		if(developedLocations.get(i).isEqual(xi, yi)) {
	   			return i;
	   		}
	   	}
		return -1;
	}

    /**
     * buildLairOrShip build a Lair Or Ship depending on the location
     * @param xi x coordinate of location
     * @param yi y coordinate of location
     * @param p player wishing to build
     * @return boolean indicating successful build
     */ 
    public boolean buyLairOrShip(int xi,int yi, Player p) {
    	int i = this.positionAvailable(xi,yi);
    	
    	if(canBuildOnLocation(xi,yi,p)) {
    		lairOrShip lairOrShip = availableLocations.get(i).getLairOrShip();
	    	if(lairOrShip == Location.lairOrShip.lair && p.buildLair()) {
	    		this.developLocation(xi,yi,p);
	    		return true;
	    	}
	    	else if(lairOrShip == Location.lairOrShip.ship && p.buildShip()) {
	    		this.developLocation(xi,yi,p);
	    		return true;
	    	}
	    	else
	    		return false; 
    	}
    	else
    		return false;
    }
    
    /**
     * canBuildOnLocation checks if a location can be built on by a player
     * @param xi x coordinate of location
     * @param yi y coordinate of location
     * @param p player wishing to build
     * @return boolean indicating if the player can build
     */ 
    private boolean canBuildOnLocation (int xi, int yi, Player p){
    	int i = this.positionAvailable(xi,yi);
    	boolean  positionAvailableToPlayer = p.locationAvailableForPlayer(xi, yi);
    	if(i!=-1 && positionAvailableToPlayer) {
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
    
    /**
     * developLocation transforms locations into Lairs or Ships
     * @param xi x coordinate of location
     * @param yi y coordinate of location
     * @param p player wishing to build
     */ 
    public boolean developLocation(int xi,int yi, Player p) {
    	if(canBuildOnLocation(xi,yi,p)) {
	    	int i = this.positionAvailable(xi,yi);
			int x = availableLocations.get(i).getX();
			int y = availableLocations.get(i).getY();
			lairOrShip lairOrShip = availableLocations.get(i).getLairOrShip();
			DevelopedLocation DL = new DevelopedLocation(x,y,lairOrShip,p);
			developedLocations.add(DL);
			p.DevelopedPlayerLocations.add(DL);
			availableLocations.remove(i);
			if(lairOrShip == Location.lairOrShip.lair)
				p.decrementUnusedLairs();
			else if(lairOrShip == Location.lairOrShip.ship)
				p.decrementUnusedShips();
			
			for(int j = 0; j < islands.size(); j++) {
				islands.get(j).developPosition(xi, yi, p);
			}
			return true;
    	}
    	else
    		return false;
    }
    
    /**
     * handleGeneratingIslandResources generates resources for a die roll matching the die number of the island
     * @param result is the number rolled by the die
     * @param p player wishing to build
     */ 
    public void handleGeneratingIslandResources(int result) {
    		for (Island island: islands) {
    			if(result == island.getDieNum()) {
    				island.generateResources();
    			}
    		}
    }
 
  	//===========================================================
  	// Methods for Printing
  	//===========================================================
    public void printEach() {
    	System.out.println("\nThe Locations on board:");
    	for(int i=0; i < availableLocations.size();i++) {
    		System.out.println(availableLocations.get(i).toString()+", i="+(i+1));    		
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
    	islands = null;
        instance = null;
    }
}