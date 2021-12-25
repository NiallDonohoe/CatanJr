package Trading;
import java.util.ArrayList;
import Board.Board;
import Board.DevelopedLocation;
import Board.Island;
import Board.Location;
import Board.Location.lairOrShip;
import CocoCards.CocoCard;
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
	public int numUsedCoco=0;
	private int numUnbuiltShips = 7;
	private int numUnbuiltLairs = 8;
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
		super(100);
		this.playerColour = playerColour;
		this.numUsedCoco = 0;
		this.intialiseResources();
	}
	
	
	//===========================================================
	// Getters and Setters
	//===========================================================
	/**
	 * getColour returns the colour of the Player.
	 * @return colour.
	 */
	public colour getColour() {
		return this.playerColour;
	}
	/**
	 * getUnbuiltLairs returns the number of unbuilt lair pieces a player has.
	 * @return int numUnbuiltLair.
	 */
	public int getUnbuiltLairs() {
		return this.numUnbuiltLairs;
	}
	/**
	 * getUnbuiltShips returns the number of unbuilt ship pieces a player has.
	 * @return int numUnbuiltShips
	 */
	public int getUnbuiltShips() {
		return this.numUnbuiltShips;
	}
	/**
	 * getNumUsedCoco returns the number of coco cards a player has used.
	 * @return int numUsedCoco
	 */
	public int getNumUsedCoco() {
		return this.numUsedCoco;
	}
	/**
	 * getPlayerDevelopedLocations returns an ArrayList of positions a player has developed.
	 * @return ArrayList<DevelopedLocation> DevelopedPlayerLocations.
	 */
	public ArrayList<DevelopedLocation> getPlayerDevelopedLocations(){
		return DevelopedPlayerLocations;
	}
	
	//===========================================================
	// Other Methods
	//===========================================================
	/**
	 * decrementUnusedLairs decrements the number of unused lairs when a lair is built.
	 */
	public void decrementUnusedLairs() {
		this.numUnbuiltLairs-=1;
	}
	/**
	 * decrementUnusedShips decrements the number of unused ships when a ship is built.
	 */
	public void decrementUnusedShips() {
		this.numUnbuiltShips-=1;
	}
	/**
	 * incrementUnusedLairs increments the number of unused lairs when a ships is unbuilt.
	 */
	public void incrementUnusedLairs() {
		this.numUnbuiltLairs+=1;
	}

	/**
	 * buyCocoCard is used to buy a coco card
	 * @return CocoCard. Returns the instance of the coco card the player drew from the CocoDeck or null.
	 */
	public CocoCard buyCocoCard() {
		
		if(CocoDeck.getInstance().checkIfCardsRemaining() && this.canAffordCocoCard()) {
			
			Stockpile.getInstance().moveResource(ResourceType.cutlass, 1, this);
			Stockpile.getInstance().moveResource(ResourceType.molasses, 1, this);
			Stockpile.getInstance().moveResource(ResourceType.gold, 1, this);
			
			CocoCard card = CocoDeck.getInstance().getCocoCard();
			card.use(this);
			this.numUsedCoco++;
			Board.getInstance().getSpookyIsland().developCocoCardLair(this);
			return card;
		}
		else if(!CocoDeck.getInstance().checkIfCardsRemaining()){
			System.out.println("There are no more Coco Cards.");
		}
		else if(!this.canAffordCocoCard())
			System.out.println(this.playerColour+" player doesn't have sufficient resources to purchase coco card.");
		return null;
	}
	/**
	 * moveGhostCaptain used to move the ghostCaptain to another island.
	 * @param x. The x coordinate of the island the ghostCaptain is being moved to. 
	 * @param y. The y coordinate of the island the ghostCaptain is being moved to.
	 */
	public void moveGhostCaptain(int x,int y) {
		for(Island island: board.getIslands()) {
			if(island.getHasGhostCaptain())
				island.setGhostCaptain(false, this.playerColour);
		}
		
		for(Island island: board.getIslands()) {
			if(island.getX() == x && island.getY() == y) {
				island.setGhostCaptain(true, this.playerColour);
			}
		}
	}
	/**
	 * buildLair decrements the resources associated with building a lair if the player has 
	 * the required resources to build.
	 * @return boolean. True if a player could build. False if they could not.
	 */
	public boolean buildLair() {
		if(this.canAffordLair()) {
			Stockpile.getInstance().moveResource(ResourceType.cutlass, 1, this);
			Stockpile.getInstance().moveResource(ResourceType.molasses, 1, this);
			Stockpile.getInstance().moveResource(ResourceType.goat, 1, this);
			Stockpile.getInstance().moveResource(ResourceType.wood, 1, this);
			System.out.println("Player has required resources to build lair.");
			return true;
		}
		else {
			System.out.println("Player does not have required resources to build lair.");
			return false;
		}
	}
	/**
	 * buildShip decrements the resources associated with building a ship if the player has 
	 * the required resources to build.
	 * @return boolean. True if a player could build. False if they could not.
	 */
	public boolean buildShip() {
		if(this.canAffordShip()) {
			Stockpile.getInstance().moveResource(ResourceType.goat, 1, this);
			Stockpile.getInstance().moveResource(ResourceType.wood, 1, this);
			System.out.println("Player has required resources to build ship.");
			return true;
		}
		else {
			System.out.println("Player does not have required resources to build ship.");
			return false;
		}
	}
	/**
	 * locationAvailableForPlayer checks if a position the player has requested to develop is adjacent to 
	 * one of their current developed positions.
	 * @param x. The x coordinate of the requested location.
	 * @param y. The y coordinate of the requested location.
	 * @return boolean. True if a player can develop, false if they cannot.
	 */
	public boolean locationAvailableForPlayer(int x, int y) {
		lairOrShip lairOrShip = null;
		int j = Board.getInstance().positionAvailable(x, y);
		if(j!=-1)
			lairOrShip = Board.getInstance().getAvailableLocations().get(j).getLairOrShip();
		
		if(lairOrShip == Location.lairOrShip.ship && this.numUnbuiltShips == 0) {
			System.out.println("The player does not have any unbuilt ships left.");
			return false;
		}
		else {
			for(int i = 0; i < this.DevelopedPlayerLocations.size(); i++) {
				int xPL = this.DevelopedPlayerLocations.get(i).getX();
				int yPL = this.DevelopedPlayerLocations.get(i).getY();
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
	}
	/**
	 * initializeResources takes 1 wood and 1 molasses from the stockpile at the start of the game.
	 */
	private void intialiseResources() {
		 this.moveResource(ResourceType.wood,1,Stockpile.getInstance());
		 this.moveResource(ResourceType.molasses,1,Stockpile.getInstance());
	}
	/**
	 * canAffordShip checks if the player has the required resources to build a ship.
	 * @return boolean. True if the player has required resources, false if they do not.
	 */
	private boolean canAffordShip() {
		return this.numGoats > 0 && this.numWood > 0;
	}
	/**
	 * canAffordLair checks if a player has the required resources to build a lair.
	 * @return boolean. True if player has the required resources, false if they do not.
	 */
	private boolean canAffordLair() {
		return this.numWood > 0 && this.numCutlasses > 0 && this.numGoats > 0 && this.numWood > 0; 	
	}
	/**
	 * canAffordCocoCard checks if a player has the required resources to buy a coco card.
	 * @return boolean. True if player can afford coco card, false if they cannot.
	 */
	private boolean canAffordCocoCard() {
		return this.numCutlasses > 0 && this.numMolasses > 0 && this.numGold > 0 ;
	}
  	//===========================================================
  	// Methods for Printing
  	//===========================================================
	public void printResources() {
		System.out.println("\n"+playerColour+" player");
		super.printResources();
	}
	public void printDevelopedLocations() {
		System.out.println("\n"+this.playerColour+" player developed locations:");
		for(int i=0;i<DevelopedPlayerLocations.size();i++) {
			System.out.println(DevelopedPlayerLocations.get(i).toString());
		}
	}
    //===========================================================
    // Abstract Singleton destroyer for unit testing ONLY
    //===========================================================
	protected abstract void destroyMe();
}
