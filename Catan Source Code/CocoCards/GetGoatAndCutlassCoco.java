package CocoCards;

import Trading.Player;
import Trading.ResourceHolder;

/**
 * Class for Coco Card giving a player goat and cutlass
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public class GetGoatAndCutlassCoco extends CocoCard{
	//===========================================================
	// Constructor
	//===========================================================
	/**
	* Constructor for a goat and cutlass coco card
	*/
	public GetGoatAndCutlassCoco() {
		this.cardType = CocoDeck.cocoCardType.getGoatAndCutlass;
	}
	
	//===========================================================
	// Methods
	//===========================================================
    /**
     * use method for a coco card. 
     * Gives player 2 goats and 2 cutlasses.
     */
	@Override
	public void use(Player player) {
		System.out.println(player.getColour()+" player gets 2 goats and 2 cutlasses");
		player.moveResource(ResourceHolder.ResourceType.goat, 2, Trading.Stockpile.getInstance());
		player.moveResource(ResourceHolder.ResourceType.cutlass, 2, Trading.Stockpile.getInstance());
	}
	
}