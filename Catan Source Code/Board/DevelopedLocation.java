package Board;
import Trading.Player;
/**
 * Class for a Developed Location
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public class DevelopedLocation extends Location{
	
	//===========================================================
	// Class Variables 
	//===========================================================
	private Player player;
	
	//===========================================================
	// Constructor
	//===========================================================
    /**
     * DevelopedLocation constructor 
     * @param x is the x coordinate of the location
     * @param y is the y coordinate of the location
     * @param lairOrShip is an enum indicating if the development is a lair or ship
     * @param player is the player developing the location
     */
	public DevelopedLocation(int x, int y, DevelopedLocation.lairOrShip lairOrShip, Player player ) {
		super(x,y,lairOrShip);
		this.player = player;
	}
	
	//===========================================================
	// Getters & Setters
	//===========================================================
    /**
     * getPlayer method.
     * @return player. The player who owns the development
     */
	public Player getPlayer() {
		return this.player;
	}
}