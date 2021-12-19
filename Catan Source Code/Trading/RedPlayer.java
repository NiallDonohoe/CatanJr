package Trading;

import Board.Board;
import Trading.Player.colour;

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
	static public RedPlayer getInstance() {
		if(instance == null)
			instance = new RedPlayer();
		return instance;
	}
	static public boolean PlayerExists() {
		if (instance == null)
			return false;
		else 
			return true;
	}
	
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