package Trading;
import java.util.ArrayList;
import Board.Board;
import Board.DevelopedLocation;
import Board.Island;
import CocoCards.CocoDeck;

/**
 * Class for Players in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */


public abstract class Player extends TradingResourceHolder{
	
	//===========================================================
	// Class Variables 
	//===========================================================
	public int numUsedCoco;
	private int numUnusedShips = 7;
	private int numUnusedLairs = 8;
    public ArrayList<DevelopedLocation> DevelopedPlayerLocations = new ArrayList<DevelopedLocation>();
    private Board board = Board.getInstance();
    private colour playerColour;
	public enum colour{
		Red,
		Orange,
		Blue,
		White
	};
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for a Player object
	 * @param playerColour  The Player's chosen colour
	 * @param numUsedCoco   The number of Coco cards used by the player, initially 0
	 */
	protected Player(colour playerColour) {
		super(1000);
		this.playerColour = playerColour;
		this.numUsedCoco = 0;
		this.intialiseResources();
	}
	
	
	//===========================================================
	// Getters and Setters
	//===========================================================
	
	public colour getColour() {
		return this.playerColour;
	}
	
	//===========================================================
	// Other Methods
	//===========================================================
	
	public void decrementUnusedLairs() {
		this.numUnusedLairs-=1;
	}
	public void decrementUnusedShips() {
		this.numUnusedLairs-=1;
	}
	
	public void buyCocoCard() {
		CocoDeck.getInstance().getCocoCard().use(this);
	}
	
	private void handleMovingGhostCaptain(int x,int y) {
		
		for(Island island: board.getIslands()) {
			if(island.checkIfHasGhostCaptain())
			island.setGhostCaptain(false, this.playerColour);
		}
		
		for(Island island: board.getIslands()) {
			if(island.getX() == x && island.getY() == y) {
				island.setGhostCaptain(true, this.playerColour);
			}
		}
	}
	
	public void moveGhostCaptain(int x, int y) {
		System.out.println("Choose where we place Ghost Captain");
		handleMovingGhostCaptain(x,y);
	}
	
	
	public void printDevelopedLocations() {
		System.out.println("\n"+this.playerColour+" player developed locations:");
		for(int i=0;i<DevelopedPlayerLocations.size();i++) {
			System.out.println(DevelopedPlayerLocations.get(i).toString());
		}
	}
	public boolean buildLair() {
		if(this.numWood > 0 && this.numCutlasses > 0 &&
				this.numGoats > 0 && this.numWood > 0 ) {
			Stockpile.getInstance().moveResource(ResourceType.cutlass, 1, this);
			Stockpile.getInstance().moveResource(ResourceType.molasses, 1, this);
			Stockpile.getInstance().moveResource(ResourceType.goat, 1, this);
			Stockpile.getInstance().moveResource(ResourceType.wood, 1, this);
			this.numUnusedLairs-=1;
			System.out.println("Player has required resources to build lair.");
			return true;
		}
		else {
			System.out.println("Player does not have required resources to build lair.");
			return false;
		}
	}
	public boolean buildShip() {
		if(this.numGoats > 0 && this.numWood > 0) {
			Stockpile.getInstance().moveResource(ResourceType.goat, 1, this);
			Stockpile.getInstance().moveResource(ResourceType.wood, 1, this);
			this.numUnusedShips-=1;
			System.out.println("Player has required resources to build ship.");
			return true;
		}
		else {
			System.out.println("Player does not have required resources to build ship.");
			return false;
		}
	}

	// Allows a player to build on a locations if they have they are adjacent to the position or if
	// they are taking their start positions.
	public boolean positionAvailableForPlayer(int x, int y) {
		for(int i = 0; i < this.DevelopedPlayerLocations.size(); i++) {
			int xPL = this.DevelopedPlayerLocations.get(i).getX();
			int yPL = this.DevelopedPlayerLocations.get(i).getY();
//			System.out.println("x="+x+","+xPL+"|| y="+y+","+yPL);
			if((x == (xPL - 1) && y == yPL) ||
				(x == (xPL +1) && y == yPL) ||	
				(x == xPL && y == (yPL -1)) ||
				(x == xPL && y == (yPL +1))) {
					return true;
			}
		}
		if(this.DevelopedPlayerLocations.size()<4)
			return true;
		else
			return false;
	}


	public void printResources() {
		System.out.println("\n"+playerColour+" player");
		super.printResources();
	}
	
	private void intialiseResources() {
		 this.moveResource(ResourceType.wood,1,Stockpile.getInstance());
		 this.moveResource(ResourceType.molasses,1,Stockpile.getInstance());
	}


    //===========================================================
    // Abstract Singleton destroyer for unit testing ONLY
    //===========================================================
	protected abstract void destroyMe();
}
