package Board;
import java.util.*;
import Trading.Player;
import Trading.Stockpile;
import Board.Location.lairOrShip;

public class Island {
	
	protected Board board = Board.getInstance();
	private Stockpile stockpile = Stockpile.getInstance();
	protected int x;
	protected int y;
	protected Trading.ResourceHolder.ResourceType resource;
	protected int die;
	private boolean hasGhostCaptain;
	private Trading.Player.colour ghostCaptainLastMovedBy;
	
	ArrayList<Location> islandAvailableLairLocations = new ArrayList<Location>();
	ArrayList<DevelopedLocation> islandDevelopedLairLocations = new ArrayList<DevelopedLocation>();
	
	ArrayList<Location> islandAvailableShipLocations = new ArrayList<Location>();
	ArrayList<DevelopedLocation> islandDevelopedShipLocations = new ArrayList<DevelopedLocation>();
	
	protected Island(int[] center, Board.corner loc, Trading.ResourceHolder.ResourceType resource, int die){
		this.x = center[0]; this.y = center[1];
	    this.resource = resource;
	    this.die = die;
	    
	    // if the island is the ghost island set the ghost captain here
	    if(center[0]==11 && center[1]==4) {
	    	this.setGhostCaptain(true,null);
	    }
	    else {
	    	this.setGhostCaptain(false,null);
	    }
	    
	    if(loc==Board.corner.Regular) {
	    	this.setLairLocations();
        	this.setShipLocations();
	    }
	    else {
	    	this.setCornerLairLocations(loc);
    		this.setCornerShipLocations(loc);
	    }
	}
	
	public void setLairLocations() {
		for(int xi = x-2; xi <= (x + 2); xi+=2)
			for(int yi = y-1; yi <= (y+1); yi+=2) {
				this.addLairToAvailableForIsland(xi, yi);
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
		this.islandAvailableShipLocations.add(Ltemp);
		board.addToBoard(Ltemp);
	}
	
	public void setGhostCaptain(boolean hereOrNot, Trading.Player.colour ghostCaptainLastMovedBy) {
		this.hasGhostCaptain = hereOrNot;
		this.ghostCaptainLastMovedBy = ghostCaptainLastMovedBy;
	}
	
	public void generateResources() {

			if(!hasGhostCaptain) { // if ghostCaptain absent, increment each lair owner's resource
				for(int i=0; i < islandDevelopedLairLocations.size();i++) {
					islandDevelopedLairLocations.get(i).getPlayer().moveResource(this.resource, 1, stockpile);
					}
				}
			
			// otherwise ghostCaptain is on island
			// we check if the lair owner placed the ghostCaptain there and increment resources.
			else {
				for(int i=0; i < islandDevelopedLairLocations.size();i++) {
					if (ghostCaptainLastMovedBy == (islandDevelopedLairLocations.get(i).getPlayer().getColour()))
					islandDevelopedLairLocations.get(i).getPlayer().moveResource(this.resource, 2, stockpile);
					}
				}
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
		System.out.println("Players with lairs on island "+this.resource+this.die+":");
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
