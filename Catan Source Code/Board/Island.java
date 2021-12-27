package Board;
import java.util.*;
import Trading.Player;
import Trading.Player.colour;
import Trading.Stockpile;
import Board.Location.lairOrShip;

/**
 * Class for an Island in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public class Island {
	//===========================================================
	// Class Variables 
	//===========================================================
	private Board board = Board.getInstance();
	private Stockpile stockpile = Stockpile.getInstance();
	protected int xC;
	protected int yC;
	private Trading.ResourceHolder.ResourceType resource;
	private int dieNum;
	private boolean hasGhostCaptain;
	private Trading.Player.colour ghostCaptainLastMovedBy;
	
	ArrayList<Location> islandAvailableLairLocations = new ArrayList<Location>();
	ArrayList<DevelopedLocation> islandDevelopedLairLocations = new ArrayList<DevelopedLocation>();
	
	ArrayList<Location> islandAvailableShipLocations = new ArrayList<Location>();
	ArrayList<DevelopedLocation> islandDevelopedShipLocations = new ArrayList<DevelopedLocation>();
	
	//===========================================================
	// Constructor
	//===========================================================
    /**
     * Island constructor 
     * @param center is the [x,y] coordinate of the island
     * @param corner is the enum indicating if the island is one of the 4 edge islands or a regular island
     * @param resource is the resource type associated with the island
     * @param die is the die number associated with the island
     */
	protected Island(int[] center, Board.corner corner, Trading.ResourceHolder.ResourceType resource, int die){
		this.xC = center[0]; this.yC = center[1];
	    this.resource = resource;
	    this.dieNum = die;
	    this.setGhostCaptain(false,null);
	   
	    if(corner==Board.corner.Regular) {
	    	this.setLairLocations();
        	this.setShipLocations();
	    }
	    else {
	    	this.setCornerLairLocations(corner);
    		this.setCornerShipLocations(corner);
	    }
	}
	
    //===========================================================
  	// Getters and Setters
  	//===========================================================
    /**
     * getX method.
     * @return island x coordinate
     */
	public int getX() { return xC; }
	 /**
     * getY method.
     * @return island y coordinate
     */
	public int getY() { return yC; }
	 /**
     * getDieNum method.
     * @return die number associated with island
     */
	public int getDieNum() {
		return dieNum;
	}
	/**
     * getResourceType method.
     * @return resource associated with island
     */
	public Trading.ResourceHolder.ResourceType getResourceType() {
		return this.resource;
	}
	
	/**
     * checkIfHasGhostCaptain method.
     * @return boolean indicating if Ghost Captain on island
     */
	public boolean getHasGhostCaptain() {
		return hasGhostCaptain;
	}
	
	/**
     * setGhostCaptain method.
     * @param hereOrNot booloean indicating Ghost Captain on the island or not
     * @param ghostCaptainLastMovedBy colour of player who last moved Ghost Captain
     */
	public void setGhostCaptain(boolean hereOrNot, Trading.Player.colour ghostCaptainLastMovedBy) {
		this.hasGhostCaptain = hereOrNot;
		this.ghostCaptainLastMovedBy = ghostCaptainLastMovedBy;
	}
	
	/**
     * colour getGhostCaptainLastMovedColour method.
     * @return colour that last moved the Ghost Captain
     */
	public colour getGhostCaptainLastMovedColour() {
		return this.ghostCaptainLastMovedBy;
	}
	
	//===========================================================
	// Methods for manipulating Island
	//===========================================================
	/**
     * setLairLocations method.
     * sets Locations for Lairs for an Island
     */
	private void setLairLocations() {
		for(int xi = xC-2; xi <= (xC + 2); xi+=2)
			for(int yi = yC-1; yi <= (yC+1); yi+=2) {
				this.addLairToAvailableForIsland(xi, yi);
			}
	}
	
	 /**
     * setShipLocations method.
     * sets Locations for Ships for an Island
     */
	private void setShipLocations() {
		for(int xi = xC-1; xi <= (xC+1); xi+=2)	
			for(int yi = yC-1; yi<= yC+1; yi+=2)
				this.addShipToAvailableForIsland(xi, yi);
		this.addShipToAvailableForIsland(xC-2, yC);
		this.addShipToAvailableForIsland(xC+2, yC);	
	}
	
	/**
     * setCornerLairLocations method.
     * sets Locations for Lairs for a Corner Island
     */
	private void setCornerLairLocations(Board.corner LOC) {
		int yi = 0, yi2 = 0, xi2 = 0;
		if(LOC==Board.corner.SW||LOC==Board.corner.SE) {
			yi = yC+1;	// The y coord of lairs for corner
			yi2= yC-1;	// The y coord of single lair for corner 
		}
		else if(LOC==Board.corner.NW||LOC==Board.corner.NE) {
			yi = yC-1;	
			yi2= yC+1;
		}
		if(LOC==Board.corner.SW||LOC==Board.corner.NW)
			xi2 = xC+2;
		else if(LOC==Board.corner.SE||LOC==Board.corner.NE)
			xi2 = xC-2;
		
		this.addLairToAvailableForIsland(xi2, yi2);
		
		for(int xi=xC-2;xi<=(xC+2);xi+=2) { 
			this.addLairToAvailableForIsland(xi, yi);
		}
	}
	
	/**
     * setCornerShipLocations method.
     * sets Locations for Ships for a Corner Island
     */
	private void setCornerShipLocations(Board.corner LOC) {
		int yi = 0, xi = 0;
		if(LOC==Board.corner.SW||LOC==Board.corner.SE) {
			yi= yC+1;	// The y coord of single lair for corner 
		}
		else if(LOC==Board.corner.NW||LOC==Board.corner.NE) {
			yi= yC-1;
		}
		if(LOC==Board.corner.SW||LOC==Board.corner.NW)
			xi = xC+2;
		else if(LOC==Board.corner.SE||LOC==Board.corner.NE)
			xi = xC-2;
		
		this.addShipToAvailableForIsland(xi, yC);
		this.addShipToAvailableForIsland(xC-1, yi);
		this.addShipToAvailableForIsland(xC+1, yi);
	}
	/**
     * addLairToAvailableForIsland method.
     * adds Lair Locations around the island to the islandAvailableLairLocations
     */
	private void addLairToAvailableForIsland(int xi,int yi) {
		Location Ltemp = new Location(xi,yi,lairOrShip.lair);
		this.islandAvailableLairLocations.add(Ltemp); 
		board.addToBoard(Ltemp);
	}
	
	/**
     * addShipToAvailableForIsland method.
     * adds Ship Locations around the island to the islandAvailableShipLocations
     */
	private void addShipToAvailableForIsland(int xi,int yi) {
		Location Ltemp = new Location(xi,yi,lairOrShip.ship);
		this.islandAvailableShipLocations.add(Ltemp);
		board.addToBoard(Ltemp);
	}
	

  	//===========================================================
  	// Other Methods
  	//===========================================================

	/**
     * generateResources method.
     * Generates resources for an island if ghost captain absent
     */
	public void generateResources() {
		
			if(!hasGhostCaptain) { // if ghostCaptain absent, increment each lair owner's resource
				for(int i=0; i < islandDevelopedLairLocations.size();i++) {
					System.out.println(islandDevelopedLairLocations.get(i).getPlayer().getColour()+" player gets " + this.resource);
					islandDevelopedLairLocations.get(i).getPlayer().moveResource(this.resource, 1, stockpile);
					}
				}
			
			// otherwise ghostCaptain is on island
			// we check if the lair owner placed the ghostCaptain there and increment resources.
			else {
				for(int i=0; i < islandDevelopedLairLocations.size();i++) {
					if (ghostCaptainLastMovedBy == (islandDevelopedLairLocations.get(i).getPlayer().getColour())) {
						System.out.println(islandDevelopedLairLocations.get(i).getPlayer().getColour()+" player gets 2 " + this.resource+"s");
						islandDevelopedLairLocations.get(i).getPlayer().moveResource(this.resource, 2, stockpile);
						}
					}
				}
			}
	
	/**
     * developPosotion method.
     * @param xi is the x coordinate of the location
     * @param yi is the y coordinate of the location
     * @param p is the player developing the position
     */
	protected void developPosition(int xi, int yi ,Player p) {
		int i = this.positionInList(xi,yi);
		lairOrShip LairOrShip;
		if(i!=-1)
			LairOrShip = this.islandAvailableLairLocations.get(i).getLairOrShip();
		else
			LairOrShip = lairOrShip.ship;
		
		if(LairOrShip == lairOrShip.lair) {
			int x = islandAvailableLairLocations.get(i).getX();
			int y = islandAvailableLairLocations.get(i).getY();
			DevelopedLocation Dlair = new DevelopedLocation(x,y,LairOrShip,p);
			this.islandDevelopedLairLocations.add(Dlair);
			this.islandAvailableLairLocations.remove(i);
		}
	}
	

	/**
     * positionInList method. Finds the index of the location in the ArrayList
     * @param xi is the x coordinate of the location
     * @param yi is the y coordinate of the location
     * @return i the index of the location in the ArrayList
     */
	// Return the position in the list of the Location
	private int positionInList(int xi, int yi) {
		for(int i=0; i < islandAvailableLairLocations.size();i++) {
			int x = islandAvailableLairLocations.get(i).getX();
			int y = islandAvailableLairLocations.get(i).getY();
        	if(x == xi && y == yi) {
    			return i;
    		}
    	}
		return -1;
    }
  	//===========================================================
  	// Printing Methods
  	//===========================================================

	// Prints the players with developed lairs on the island.
	public void playersWithDevelopedLairs() {
		System.out.println("Players with lairs on island "+this.resource+this.dieNum+":");
		for(int i=0; i < islandDevelopedLairLocations.size();i++) {
			int x = this.islandDevelopedLairLocations.get(i).getX();
			int y = this.islandDevelopedLairLocations.get(i).getY();
			System.out.println(this.islandDevelopedLairLocations.get(i).getPlayer().getColour()+": "+x+","+y);
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
	    return "Center: " + this.xC + " " + this.yC + "available " + islandAvailableLairLocations;
	}
}
