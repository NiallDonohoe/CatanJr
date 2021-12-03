package board;
import java.util.*;

import Trading.Player;
import board.Board;
import board.Location.lairOrShip;
public class Island {

	protected int x;
	protected int y;
	protected Board.resource res;
	protected int die;
	ArrayList<Location> islandAvailableLairLocations = new ArrayList<Location>();
	ArrayList<DevelopedLocation> islandDevelopedLairLocations = new ArrayList<DevelopedLocation>();
	private Board board = Board.getInstance();
	
	public Island(int[] center, Board.corner loc, Board.resource res, int die){
		this.x = center[0];
	    this.y = center[1];
	    this.res = res;
	    this.die = die;
	    switch (loc) {
	        case NE: 
	        case SE:
	        case SW:
	        case NW: 
	        	this.setCornerLairLocations(loc);
//	        	this.setCornerShipLocations(loc);
	        	break;
	
	        default:
	        	this.setLairLocations();
//	        	this.setShipLocations();
	        	break;
	    }
	}
	public void setLairLocations() {
	//	System.out.println("These are what the outputs should be.");
		for(int xi = x-2; xi <= (x + 2); xi+=2)
			for(int yi = y-1; yi <= (y+1); yi+=2) {
				this.addLairToAvailableForIsland(xi, yi);
	//			System.out.println("x="+xi+", y="+yi);
			}
	}
	public void setShipLocations() {
		for(int xi = x-1; xi <= (x+1); xi+=2)	
			for(int yi = y-1; yi<= y+1; yi+=2)
				this.addShipToAvailableForIsland(xi, yi);
		this.addShipToAvailableForIsland(x-2, y);
		this.addShipToAvailableForIsland(x+2, y);
	  }
	public void setCornerLairLocations(Board.corner LOC) {
	//	System.out.println("These are what the outputs should be (corner).");
	//	System.out.println("x="+x+", y="+y);
		// Sets the lairs for a corner location.
		int yi = 0, yi2 = 0, xi2 = 0;
		if(LOC==Board.corner.SW||LOC==Board.corner.SE) {
			yi = y+1;	// The y coord of lairs for corner
			yi2= y-1;	// The y coord of single lair for corner 
		}
		else if(LOC==Board.corner.NW||LOC==Board.corner.NE) {
			yi = y-1;	
			yi2= y+1;
		}
		if(LOC==Board.corner.SW||LOC==Board.corner.NW)
			xi2 = x+2;
		else if(LOC==Board.corner.SE||LOC==Board.corner.NE)
			xi2 = x-2;
		
		this.addLairToAvailableForIsland(xi2, yi2);
		
		for(int xi=x-2;xi<=(x+2);xi+=2) { 
			this.addLairToAvailableForIsland(xi, yi);
	//		System.out.println("x="+x+", y="+y);
		}
	}
	public void setCornerShipLocations(Board.corner LOC) {
		int yi = 0, xi = 0;
		if(LOC==Board.corner.SW||LOC==Board.corner.SE) {
			yi= y+1;	// The y coord of single lair for corner 
		}
		else if(LOC==Board.corner.NW||LOC==Board.corner.NE) {
			yi= y-1;
		}
		if(LOC==Board.corner.SW||LOC==Board.corner.NW)
			xi = x+2;
		else if(LOC==Board.corner.SE||LOC==Board.corner.NE)
			xi = x-2;
		
		this.addShipToAvailableForIsland(xi, y);
		this.addShipToAvailableForIsland(x-1, yi);
		this.addShipToAvailableForIsland(x+1, yi);
	}
	public void addLairToAvailableForIsland(int xi,int yi) {
		Location Ltemp = new Location(xi,yi,lairOrShip.lair);
		this.islandAvailableLairLocations.add(Ltemp); 
		board.addToBoard(Ltemp);
	}
	public void addShipToAvailableForIsland(int xi,int yi) {
		Location Ltemp = new Location(xi,yi,lairOrShip.ship);
		board.addToBoard(Ltemp);
	}
	public void printEach() {
		System.out.println("\nThe Locations in island:");
		for(int i=0; i < islandAvailableLairLocations.size();i++) {
			int[] temp = islandAvailableLairLocations.get(i).getLocation();
			System.out.println("x="+temp[0]+", y="+temp[1]);
		}
	}
	@Override
	public String toString(){
	    return "Center: " + this.x + " " + this.y + "available " + islandAvailableLairLocations;
	}
	// Develop an available position. If it is a lair.
	public void developPosition(int xi, int yi ,Player p) {
		int i = this.PositionInList(xi,yi);
		lairOrShip los = this.islandAvailableLairLocations.get(i).LairOrShip();
		if(i!=-1 && los == lairOrShip.lair) {
			int x = islandAvailableLairLocations.get(i).getX();
			int y = islandAvailableLairLocations.get(i).getY();
			DevelopedLocation Dlair = new DevelopedLocation(x,y,los,p);
			this.islandDevelopedLairLocations.add(Dlair);
			this.islandAvailableLairLocations.remove(i);
		}
	}
	// Prints the players with developed lairs on the island.
	public void playersWithDevelopedLairs() {
		System.out.println("Players with lairs on island "+this.res+this.die+":");
		for(int i=0; i < islandDevelopedLairLocations.size();i++) {
			x = this.islandDevelopedLairLocations.get(i).getX();
			y = this.islandDevelopedLairLocations.get(i).getY();
			System.out.println(this.islandDevelopedLairLocations.get(i).getPlayer().getColour()+": "+x+","+y);
		}
	}
	// Return the position in the list of the Location
	public int PositionInList(int xi, int yi) {
		for(int i=0; i < islandAvailableLairLocations.size();i++) {
			x = islandAvailableLairLocations.get(i).getX();
			y = islandAvailableLairLocations.get(i).getY();
        	if(x == xi && y == yi) {
    			return i;
    		}
    	}
		return -1;
    }
}
