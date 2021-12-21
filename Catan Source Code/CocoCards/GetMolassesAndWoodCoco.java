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
	
	@Override
	public void use(Player player) {
		player.moveResource(ResourceHolder.ResourceType.molasses, 2, Trading.Stockpile.getInstance());
		player.moveResource(ResourceHolder.ResourceType.wood, 2, Trading.Stockpile.getInstance());
	}

}