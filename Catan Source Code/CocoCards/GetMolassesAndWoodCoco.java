package CocoCards;

import Trading.Player;
import Trading.ResourceHolder;

/**
 * Class for Coco Card giving a player molasses and wood
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public class GetMolassesAndWoodCoco extends CocoCard{
	//===========================================================
	// Constructor
	//===========================================================
	/**
	* Constructor for a molasses and wood Coco Card
	*/
	public GetMolassesAndWoodCoco() {
		this.cardType = CocoDeck.cocoCardType.getMolassesAndWood;
	}
	
	//===========================================================
	// Methods
	//===========================================================
    /**
     * use method for a coco card. 
     * Gives player 2 molasses and 2 wood.
     */
	@Override
	public void use(Player player) {
		System.out.println("Player "+ player.getColour() + " gets 2 molasses and 2 wood");
		player.moveResource(ResourceHolder.ResourceType.molasses, 2, Trading.Stockpile.getInstance());
		player.moveResource(ResourceHolder.ResourceType.wood, 2, Trading.Stockpile.getInstance());
	}

}