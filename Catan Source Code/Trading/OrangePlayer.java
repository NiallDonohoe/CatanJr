package Trading;

import Board.Board;
/**
 * Class for OrangePlayer in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */
public class OrangePlayer extends Player {
	//===========================================================
	// Class Variables 
	//===========================================================
    static OrangePlayer instance = null;
    
	//===========================================================
	// Constructor - Singleton
	//===========================================================
	private OrangePlayer() {
		super(colour.Orange);
	}
	
  	//===========================================================
  	// Other Methods
  	//===========================================================
    /**
     * getInstance method returns single instance of OrangePlayer.
     * @return OrangePlayer. Singleton OrangePlayer object.
     */
	static public OrangePlayer getInstance() {
		if(instance == null)
			instance = new OrangePlayer();
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
    	Board.getInstance().developLocation(3,3,this);	// first lair
    	Board.getInstance().developLocation(4,3,this);	// first ship
		Board.getInstance().developLocation(15,7,this);	// second lair
		Board.getInstance().developLocation(15,6,this);	// second ship
    }
    
    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    public void destroyMe() {
        instance = null;
    }
}
