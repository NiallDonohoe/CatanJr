package board;
import java.util.*;

import Trading.Player;
import board.Location.lairOrShip;

public class Board {

    public enum corner {
        NE, SE, SW, NW, Regular	// feature of island would probably be better in island
    }
    public enum resource{
    	wood, goat, gold, molasses, cutlass, none;
    }
    public enum location{
    	none,red,blue;
    }
    private static Board instance = new Board(); 
    private static ArrayList<Island> islands = new ArrayList<Island>();
    public static ArrayList<Location> availableLocations = new ArrayList<Location>();	
    public static ArrayList<DevelopedLocation> developedLocations = new ArrayList<DevelopedLocation>();
    
    // private constructor, singleton
    private Board() {}
    
    // singleton
    public static Board getInstance() {
    	return instance;
    }
    public void addIsland(int[] center, Board.corner loc, Board.resource res, int die) {
    	Island island = new Island(center,loc,res,die);
    	this.islands.add(island);
    }
    public boolean addToBoard(Location l) {
    	for(int i=0; i < availableLocations.size();i++) {
    		if(availableLocations.get(i).isEqual(l.getX(),l.getY()))
    			return false;
    	}
    	availableLocations.add(l);
    	return true;

    }
    public void printEach() {
    	System.out.println("\nThe Locations on board:");
    	for(int i=0; i < availableLocations.size();i++) {
    		System.out.println(availableLocations.get(i).toString()+", i="+(i+1));    		
    	}
    }
    // Returns -1 if a position is not available if it is then 
    // it returns the position of that location in the arraylist
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
    // if the Location has not been developed then
	// remove from available locations on board
	// add to developedLocations for board, island
	// and player
    public void developPosition(int xi, int yi,Player p) {
    	int i = this.PositionAvailable(xi,yi);
    	if(i!=-1) {
    		int x = availableLocations.get(i).getX();
    		int y = availableLocations.get(i).getY();
    		lairOrShip los = availableLocations.get(i).LairOrShip();
    		DevelopedLocation DL = new DevelopedLocation(x,y,los,p);
    		this.developedLocations.add(DL);
    		p.DevelopedPlayerLocations.add(DL);
    		this.availableLocations.remove(i);
    		
    		for(int j = 0; j < islands.size(); j++) {
    			islands.get(j).developPosition(xi, yi, p);
    		}
    	}
    }
    // this is for making sure that each of the is having a position properly developed
    public void developedLairsEachIsland() {
    	for(int i = 0; i< islands.size();i++)
    		islands.get(i).playersWithDevelopedLairs();
    }
}
