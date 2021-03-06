package CocoCards;

import Trading.Player;

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
	
	//===========================================================
	// Methods
	//===========================================================
    /**
     * use method for a coco card. 
     * Allows player to build a free lair or ship, this is handled through the GUI
     */
	@Override
	public void use(Player player) {
		System.out.println("Player can choose to build a Ship or Lair for free!!!");
		// Building a Lair or Ship is handled in the GUI
	}

}
