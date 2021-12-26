package Trading;

import Board.Board;

/**
 * Class for BluePlayer in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */
public class BluePlayer extends Player {
	//===========================================================
	// Class Variables 
	//===========================================================
    static BluePlayer instance = null;
    
	//===========================================================
	// Constructor - Singleton
	//===========================================================
	private BluePlayer() {
		super(colour.Blue);
	}
	
  	//===========================================================
  	// Other Methods
  	//===========================================================
    /**
     * getInstance method returns single instance of BluePlayer.
     * @return BluePlayer. Singleton BluePlayer object.
     */
	static public BluePlayer getInstance() {
		if(instance == null)
			instance = new BluePlayer();
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
    	Board.getInstance().developLocation(7,1,this);	// first lair
    	Board.getInstance().developLocation(7,2,this);	// first ship
    	Board.getInstance().developLocation(19,5,this);	// second lair
		Board.getInstance().developLocation(18,5,this);	// second ship
    }
    
    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    public void destroyMe() {
        instance = null;
    }
}