package CocoCards;

import Trading.Player;
import Trading.ResourceHolder;

/**
 * Class for Coco Card giving a player a Ship or Lair
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public class GetShipOrLairCoco extends CocoCard{
	//===========================================================
	// Constructor
	//===========================================================
	/**
	* Constructor for a shipOrLair Coco Card
	*/
	public GetShipOrLairCoco() {
		this.cardType = CocoDeck.cocoCardType.getShipOrLair;
	}

	public void use(Player player) {
		player.BuildOptions();
	}

}