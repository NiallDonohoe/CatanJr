package Trading;

import Board.Board;
import Trading.Player.colour;

public class RedPlayer extends Player{
    static RedPlayer instance = null;
    
	private RedPlayer() {
		super(colour.Red);
	}
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
    public void destroyMe() {
        instance = null;
    }
    public void developStartingPositions() {
    	Board.getInstance().developPosition(19,3,this);	// first lair
    	Board.getInstance().developPosition(18,3,this);	// first ship
		Board.getInstance().developPosition(7,7,this);	// second lair
		Board.getInstance().developPosition(7,6,this);	// second ship
    }
}