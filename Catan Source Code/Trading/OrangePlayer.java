package Trading;

import Board.Board;
import Trading.Player.colour;

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
	static public OrangePlayer getInstance() {
		if(instance == null)
			instance = new OrangePlayer();
		return instance;
	}
	static public boolean PlayerExists() {
		if (instance == null)
			return false;
		else 
			return true;
	}

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
