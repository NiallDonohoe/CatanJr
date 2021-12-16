package Trading;

import Board.Board;
import Trading.Player.colour;

public class OrangePlayer extends Player {
    static OrangePlayer instance = null;
    
	private OrangePlayer() {
		super(colour.Orange);
	}
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
    public void destroyMe() {
        instance = null;
    }
    public void developStartingPositions() {
    	Board.getInstance().developPosition(3,3,this);	// first lair
    	Board.getInstance().developPosition(4,3,this);	// first ship
		Board.getInstance().developPosition(15,7,this);	// second lair
		Board.getInstance().developPosition(15,6,this);	// second ship
    }
}
