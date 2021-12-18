package Trading;

import Board.Board;

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
	static public BluePlayer getInstance() {
		if(instance == null)
			instance = new BluePlayer();
		return instance;
	}
	static public boolean PlayerExists() {
		if (instance == null)
			return false;
		else 
			return true;
	}

    public void developStartingPositions() {
    	Board.getInstance().developPosition(7,1,this);	// first lair
    	Board.getInstance().developPosition(7,2,this);	// first ship
    	Board.getInstance().developPosition(19,5,this);	// second lair
		Board.getInstance().developPosition(18,5,this);	// second ship
    }
    
    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    public void destroyMe() {
        instance = null;
    }
}