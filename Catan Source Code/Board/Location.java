package Board;
/**
 * Class for Location in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public class Location {
	//===========================================================
	// Class Variables 
	//===========================================================
	private int x;
	private int y;
	private int[] location = new int[] {x,y};
	private lairOrShip lairOrShip;
	public enum lairOrShip{
		lair,
		ship
	}	
	//===========================================================
	// Constructor
	//===========================================================
    /**
     * Location constructor 
     * @param x is the x coordinate of the location
     * @param y is the y coordinate of the location
     * @param lairOrShip is an enum indication if the location is a Lair or Ship
     */
	public Location(int x,int y, lairOrShip lairOrShip) {
		this.x = x; this.y = y;
		this.location = new int[] {x,y};
		this.lairOrShip = lairOrShip;
	}
	
	//===========================================================
	// Getters & Setters
	//===========================================================
	/**
	 * getLocation method.
     * @return location. [x,y] of the location
     */
	public int[] getLocation() {
		return location;
	}
	
	 /**
     * getX method.
     * @return x. x coordinate of the location
     */
	public int getX() {
		return x;
	}
	
	 /**
     * getY method.
     * @return y. y coordinate of the location
     */
	public int getY() {
		return y;
	}
	
	 /**
     * getLairOrShip method.
     * @return lairOrShip. Enum indication if location is a Lair or Ship location
     */
	public lairOrShip getLairOrShip() {
		return this.lairOrShip;
	}
	
	//===========================================================
	// Other Methods
	//===========================================================
	public boolean isEqual(int x, int y) {
		if (this.x==x && this.y==y)
			return true;
		else
			return false;
	}

	//===========================================================
	// Printing Methods
	//===========================================================
	
	public String toString() {
		return "x="+this.x+", y="+this.y+", "+this.lairOrShip;
	}
}
