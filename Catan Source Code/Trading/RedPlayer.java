package Trading;

import Board.Board;
/**
 * Class for RedPlayer in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */
public class RedPlayer extends Player{
	//===========================================================
	// Class Variables 
	//===========================================================
    static RedPlayer instance = null;
    
	//===========================================================
	// Constructor - Singleton
	//===========================================================
	private RedPlayer() {
		super(colour.Red);
	}
	
  	//===========================================================
  	// Other Methods
  	//===========================================================
    /**
     * getInstance method returns single instance of RedPlayer.
     * @return RedPlayer. Singleton OrangePlayer object.
     */
	static public RedPlayer getInstance() {
		if(instance == null)
			instance = new RedPlayer();
		return instance;
	}
	/**
	 * playerExists used to check if an instance of this player exists without
	 * instantiating that instance.
	 * @return boolean indicating whether this Player object has been instantiated. 
	 */
	static public boolean playerExists() {
		if (instance == null)
			return false;
		else 
			return true;
	}
	/**
	 * developStartingPositions develops the starting positions of the player.
	 */
    public void developStartingPositions() {
    	Board.getInstance().developLocation(19,3,this);	// first lair
    	Board.getInstance().developLocation(18,3,this);	// first ship
		Board.getInstance().developLocation(7,7,this);	// second lair
		Board.getInstance().developLocation(7,6,this);	// second ship
    }
    
    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    public void destroyMe() {
        instance = null;
    }
}