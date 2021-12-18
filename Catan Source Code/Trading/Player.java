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
		super(0);
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
	
	public void BuildOptions() {
		System.out.print(this.playerColour+" can build on spaces:");
		for(int i=0;i<DevelopedPlayerLocations.size();i++) {
			int[] temp = DevelopedPlayerLocations.get(i).getLocation();
			System.out.println("\nx="+temp[0]+", y="+temp[1]);
			int x1=temp[0]-1, y1=temp[1];
			int x2=temp[0]+1, y2=temp[1];
			int x3=temp[0], y3=temp[1]-1;
			int x4=temp[0], y4=temp[1]+1;
			if(board.PositionAvailable(x1,y1)!=-1)
				System.out.println("x="+x1+". y="+y1);
			if(board.PositionAvailable(x1,y1)!=-1)
				System.out.println("x="+x2+". y="+y2);
			if(board.PositionAvailable(x3, y3)!=-1)
				System.out.println("x="+x3+". y="+y3);
			if(board.PositionAvailable(x4, y4)!=-1)
				System.out.println("x="+x4+". y="+y4);
		}
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
