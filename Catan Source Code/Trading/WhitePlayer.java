package Trading;

import Board.Board;
/**
 * Class for WhitePlayer in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */
public class WhitePlayer extends Player {
	//===========================================================
	// Class Variables 
	//===========================================================
    static WhitePlayer instance = null;
    
	//===========================================================
	// Constructor - Singleton
	//===========================================================
	private WhitePlayer() {
		super(colour.White);
	}
	
  	//===========================================================
  	// Other Methods
  	//===========================================================
    /**
     * getInstance method returns single instance of WhitePlayer.
     * @return WhitePlayer. Singleton OrangePlayer object.
     */
	static public WhitePlayer getInstance() {
		if(instance == null)
			instance = new WhitePlayer();
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
    	Board.getInstance().developLocation(15,1,this);	// first lair
    	Board.getInstance().developLocation(15,2,this);	// first ship
		Board.getInstance().developLocation(3,5,this);	// second lair
		Board.getInstance().developLocation(4,5,this);	// second ship
    }
    
    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    public void destroyMe() {
        instance = null;
    }
}
