package Trading;

import Board.Board;
import Trading.Player.colour;

public class WhitePlayer extends Player {
	//===========================================================
	// Class Variables 
	//===========================================================
    static WhitePlayer instance = null;
    
	//===========================================================
	// Constructor - Singleton
	//===========================================================
	public WhitePlayer() {
		super(colour.White);
	}
	
  	//===========================================================
  	// Other Methods
  	//===========================================================
	static public WhitePlayer getInstance() {
		if(instance == null)
			instance = new WhitePlayer();
		return instance;
	}
	static public boolean PlayerExists() {
		if (instance == null)
			return false;
		else 
			return true;
	}

    public void developStartingPositions() {
    	Board.getInstance().developPosition(15,1,this);	// first lair
    	Board.getInstance().developPosition(15,2,this);	// first ship
		Board.getInstance().developPosition(3,5,this);	// second lair
		Board.getInstance().developPosition(4,5,this);	// second ship
    }
    
    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    public void destroyMe() {
        instance = null;
    }
}
