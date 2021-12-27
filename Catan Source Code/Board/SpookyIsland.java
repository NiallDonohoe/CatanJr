package Board;

import Board.Location.lairOrShip;
import Trading.Player;
/**
 * Class for SpookyIsland in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public class SpookyIsland extends Island {
	//===========================================================
	// Class Variables 
	//===========================================================
    private static SpookyIsland instance = null;
	private DevelopedLocation cocoCardLair = null;
	private int numMostCocoCards = 0;
	
	//===========================================================
	// Constructor
	//===========================================================
	private SpookyIsland() {
		super(new int[]{11,4}, Board.corner.Regular,Trading.ResourceHolder.ResourceType.none,6);
	}
    /**
     * getInstance method returns single instance of SpookyIsland.
     * @return SSpookyIsland. Singleton SpookyIsland object.
     */
    public static SpookyIsland getInstance() {
		if(instance == null) {
			instance = new SpookyIsland();
		}
		return instance;
	}
	
	//===========================================================
	// Getters & Setters
	//===========================================================
	/**
	 * getCocoCardLair Returns the DevelopedLocation cocoCardLair.
	 * @return DevelopedLocation. Returns the cocoCardLair.
	 */
	public DevelopedLocation getCocoCardLair() {
		return cocoCardLair;
	}
	//===========================================================
	// Other Methods
	//===========================================================
    /**
     * developCocoCardLair attempts to develop the cocoCardLair. Called every time a coco card is used.
     * @param p The player attempting to develop the cocoCardLair
     * @return boolean. Returns true if this player has developed the cocoCardLair, false if they 
     * were not able to.
     */
	public boolean developCocoCardLair(Player p) {
		// if the location has already been developed
		if(cocoCardLair!=null) {
			Player CocoLairHolder = this.cocoCardLair.getPlayer();
			if(cocoCardLair.getPlayer() == p)
				return true;
			else if(cocoCardLair.getPlayer().getNumUsedCoco() == p.getNumUsedCoco()) {
				Board.getInstance().getDevelopedLocations().remove(cocoCardLair);
				cocoCardLair.getPlayer().incrementUnusedLairs();
				this.cocoCardLair = null;
				System.out.println(p.getColour()+" is now tied for most cocoCards with "+CocoLairHolder.getColour()+".");
				System.out.println(CocoLairHolder.getColour()+" loses their lair on spooky island.");
				return false;
			}
			else if(CocoLairHolder.getNumUsedCoco()>p.getNumUsedCoco())
				return false;
		}
		else {
			if(p.getNumUsedCoco() > numMostCocoCards) {
				this.numMostCocoCards = p.getNumUsedCoco();
				this.cocoCardLair = new DevelopedLocation(this.xC,this.yC,lairOrShip.lair,p);
				Board.getInstance().getDevelopedLocations().add(cocoCardLair);
				p.decrementUnusedLairs();
				System.out.println(p.getColour()+" now has the most coco cards. They are given the lair on spooky island.");
				return true;
			}
			else
				return false;
		}
		return false;
	}
}

