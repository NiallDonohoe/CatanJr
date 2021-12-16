package Trading;

import Board.Board;
import Trading.Player.colour;

public class WhitePlayer extends Player {
    static WhitePlayer instance = null;
    private int x1=15,y1=1,x2=3,y2=5;
    
	private WhitePlayer() {
		super(colour.Orange);
	}
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
    public void destroyMe() {
        instance = null;
    }
    public void developStartingPositions() {
    	Board.getInstance().developPosition(15,1,this);	// first lair
    	Board.getInstance().developPosition(15,2,this);	// first ship
		Board.getInstance().developPosition(3,5,this);	// second lair
		Board.getInstance().developPosition(4,5,this);	// second ship
    }
}
